package com.hyva.idm.pkt.pktService;

import com.google.gson.Gson;
import com.hyva.idm.pkt.pktBeans.*;
import com.hyva.idm.pkt.pktMappers.*;
import com.hyva.idm.pkt.pktRelations.*;
import com.hyva.idm.pkt.pktRepositories.*;
import com.hyva.idm.sass.sassentities.PktPermission;
import com.hyva.idm.sass.sassentities.PktUser;
import com.hyva.idm.sass.sassentities.SassSubscriptions;
import com.hyva.idm.sass.sassentities.WebsitePermissions;
import com.hyva.idm.sass.sassmapper.PktUserMapper;
import com.hyva.idm.sass.sassmapper.SassSubscriptionMapper;
import com.hyva.idm.sass.sasspojo.*;
import com.hyva.idm.sass.sassrespositories.PktUserRepository;
import com.hyva.idm.sass.sassrespositories.WebsitePermissionsRepository;
import com.hyva.idm.sass.sassutil.ObjectMapperUtils;
import com.hyva.idm.util.FileSystemOperations;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;

/**
 * krishna
 */
@Service
public class PktService {
    int paginatedConstants = 10;
    @Autowired
    PktPermissionsRepository pktPermissionRepository;
    @Autowired
    ApplicationsRepository applicationRepository;
    @Autowired
    OperatorsRepository operatorRepository;
    @Autowired
    ValidatorsRepository validatorRepository;
    @Autowired
    ActionsRepository actionRepository;
    @Autowired
    ColumnTypesRepository columnTypeRepository;
    @Autowired
    PositionsRepository positionRepository;
    @Autowired
    PktFieldRepository pktFieldsRepository;
    @Autowired
    PktBuilderRepository pktBuilderRepository;
    @Autowired
    PktUserRepository pktUserRepository;
    @Autowired
    WebsitePermissionsRepository websitePermissionsRepository;


    //Save PKT Permission Table
    @Transactional
    public PktPermissions savePktPermsissionTable(PktPermissionBean pktPermissionBean) {
        PktPermissions pktPermission = PktPermissionMapper.mapPktPermissionPojoToEnity(pktPermissionBean);
        PktPermissions permission = null;
        if (pktPermission.getPktPermissionId() != null) {
            pktPermission.setPktPermissionId(pktPermission.getPktPermissionId());
            permission = pktPermissionRepository.findAllByKeySubscriptionAndKeyNameAndPktPermissionIdNotIn(pktPermission.getKeySubscription(), pktPermission.getKeyName(), pktPermission.getPktPermissionId());
        } else {
            permission = pktPermissionRepository.findAllByKeySubscriptionAndKeyName(pktPermission.getKeySubscription(), pktPermission.getKeyName());
        }
        if (permission == null) {
            pktPermissionRepository.save(pktPermission);
        } else {
            return null;
        }
        return pktPermission;

    }

    @Transactional
    public BasePojo getPaginatedTableList(BasePojo basePojo, String searchText, String searchText1, String searchText2) {
        List<PktPermissions> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "pktPermissionId"));
//        if (basePojo.isLastPage() == true) {
//            List<PktPermission> list12 = new ArrayList<>();
//            if (!StringUtils.isEmpty(searchText)||
//                    (!StringUtils.isEmpty(searchText1)||
//                            (!StringUtils.isEmpty(searchText2)))){
//                list12 = getList1(searchText,searchText1,searchText2);
////                list1 = pktRepository.findAllByKeySubscriptionOrKeyNameOrKeyValue(searchText,searchText1,searchText2);
//            } else {
//                list12 = pktRepository.findAll();
//            }
//        }
        if (basePojo.isLastPage() == true) {
            List<PktPermissions> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText.trim()) ||
                    (StringUtils.isNotBlank(searchText1) ||
                            (StringUtils.isNotBlank(searchText2))))

            {
                list1 = getList1(searchText, searchText1, searchText2);
//                list1 = pktRepository.findAllByKeyName(searchText);
            } else {
                list1 = pktPermissionRepository.findAllByKeyGroup(null);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        PktPermissions fields = new PktPermissions();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "pktPermissionId"));
        }
        if (!StringUtils.isEmpty(searchText.trim()) ||
                (StringUtils.isNotBlank(searchText1) ||
                        (StringUtils.isNotBlank(searchText2))))

        {
            fields = getFirstObject1(sort, searchText, searchText1, searchText2);
            list = getPaginatedList1(pageable, searchText, searchText1, searchText2);
//            fields = pktRepository.findFirstByKeyNameOrKeySubscriptionOrKeyValue(searchText,sort,searchText1,searchText2);
//            list = pktRepository.findAllByKeyNameOrKeySubscriptionOrKeyValue(searchText, pageable,searchText1,searchText2);
        } else {
            fields = pktPermissionRepository.findFirstBy(sort);
            list = pktPermissionRepository.findByKeyGroup(null, pageable);
        }
        if (fields != null) {
            if (list.contains(fields)) {
                basePojo.setStatus(true);
            } else {
                basePojo.setStatus(false);
            }
            List<PktPermissionBean> pojo = PktPermissionMapper.mapEntityToPojo(list);
            basePojo = calculatePagination(basePojo, pojo.size());
            basePojo.setList(pojo);
            return basePojo;
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktPermissions> getList1(String searchText, String searchText1, String searchText2) {
        List<PktPermissions> list = pktPermissionRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText1.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"), "%" + searchText1.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText2.trim())) {
                    Predicate predicates = cb.like(root.get("keyValue"), "%" + searchText2.trim() + "%");
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Transactional
    public PktPermissions getFirstObject1(Sort sort, String searchText, String searchText1, String searchText2) {
        List<PktPermissions> list = pktPermissionRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText1.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"), "%" + searchText1.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText2.trim())) {
                    Predicate predicates = cb.like(root.get("keyValue"), "%" + searchText2.trim() + "%");
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, sort);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktPermissions> getPaginatedList1(Pageable pageable, String searchText, String searchText1, String searchText2) {
        Page<PktPermissions> list = pktPermissionRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText1.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"), "%" + searchText1.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText2.trim())) {
                    Predicate predicates = cb.like(root.get("keyValue"), "%" + searchText2.trim() + "%");
                    predicatesList.add(predicates);
                }
                Predicate predicates = cb.isNull(root.get("keyGroup"));
                predicatesList.add(predicates);
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, pageable);
        if (list.getContent().size() > 0) {
            return list.getContent();
        } else {
            return null;
        }
    }

    @Transactional
    public BasePojo calculatePagination(BasePojo basePojo, int size) {
        if (basePojo.isLastPage() == true) {
            basePojo.setFirstPage(false);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(false);
        } else if (basePojo.isFirstPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(true);
            if (basePojo.isStatus() == true) {
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        } else if (basePojo.isNextPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setPrevPage(false);
            basePojo.setNextPage(false);
            if (basePojo.isStatus() == true) {
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        } else if (basePojo.isPrevPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(false);
            if (basePojo.isStatus() == true) {
                basePojo.setPrevPage(true);
                basePojo.setFirstPage(true);
            }
        }
        if (size == 0) {
            basePojo.setLastPage(true);
            basePojo.setFirstPage(true);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(true);
        }
        return basePojo;
    }

    @Transactional
    public List<ApplicationBean> getApplicationList() {
        List<Application> applications = (List<Application>) applicationRepository.findAllByStatus("Active");
        List<ApplicationBean> applicationPojoList = ObjectMapperUtils.mapAll(applications, ApplicationBean.class);
        return applicationPojoList;
    }

    @Transactional
    public List<PktUserPojo> getPktuserList() {
        List<PktUser> pktUsers = (List<PktUser>) pktUserRepository.findAll();
        List<PktUserPojo> pktUserPojoList = ObjectMapperUtils.mapAll(pktUsers, PktUserPojo.class);
        return pktUserPojoList;
    }

    @Transactional
    public Operators saveOperator(OperatorBean operatorpojo) {
        Operators operator = null;
        if (operatorpojo.getId() != null) {
            operator = operatorRepository.findByNameAndIdNotIn(operatorpojo.getName(), operatorpojo.getId());
        } else {

            operator = operatorRepository.findByName(operatorpojo.getName());
        }
        if (operator == null) {
            Operators operators = OperatorMapper.mapOperatorPojoToEnity(operatorpojo);
            operatorRepository.save(operators);
            return operators;
        } else {
            return null;
        }
    }

    @Transactional
    public BasePojo getPaginatedOperatorList(BasePojo basePojo, String searchText) {
        List<Operators> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<Operators> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = operatorRepository.findAllByNameContaining(searchText);
            } else {
                list1 = operatorRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        Operators operator = new Operators();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            operator = operatorRepository.findFirstByNameContaining(searchText, sort);
            list = operatorRepository.findAllByNameContaining(searchText, pageable);
        } else {
            operator = operatorRepository.findFirstBy(sort);
            list = operatorRepository.findAllBy(pageable);
        }
        if (list.contains(operator)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<OperatorBean> operatorpojoList = OperatorMapper.mapOperatorEntityToPojo(list);
        basePojo = calculatePagination(basePojo, operatorpojoList.size());
        basePojo.setList(operatorpojoList);
        return basePojo;
    }

    @Transactional
    public Validator saveValidator(ValidatorBean validatorPojo) {
        Validator validator = null;
        if (validatorPojo.getId() != null) {
            validator = validatorRepository.findByNameAndIdNotIn(validatorPojo.getName(), validatorPojo.getId());
        } else {
            validator = validatorRepository.findByName(validatorPojo.getName());
        }
        if (validator == null) {
            validator = ValidatorMapper.mapValidatorPojoToEnity(validatorPojo);
            if (validator.getId() != null) {
                validator.setId(validatorPojo.getId());
            }
            validatorRepository.save(validator);
            return validator;
        } else {
            return null;

        }
    }

    @Transactional
    public Actions saveAction(ActionBean actionBean) {
        Actions action = null;
        if (actionBean.getId() != null) {
            action = actionRepository.findByNameAndIdNotIn(actionBean.getName(), actionBean.getId());
        } else {
            action = actionRepository.findByName(actionBean.getName());
        }
        if (action == null) {
            action = ActionMapper.mapActionPojoToEnity(actionBean);
            if (action.getId() != null) {
                action.setId(actionBean.getId());
            }
            actionRepository.save(action);
            return action;
        } else {
            return null;

        }
    }

    @Transactional
    public BasePojo getPaginatedActionList(BasePojo basePojo, String searchText) {
        List<Actions> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<Actions> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = actionRepository.findAllByNameContaining(searchText);
            } else {
                list1 = actionRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        Actions action = new Actions();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            action = actionRepository.findFirstByNameContaining(searchText, sort);
            list = actionRepository.findAllByNameContaining(searchText, pageable);
        } else {
            action = actionRepository.findFirstBy(sort);
            list = actionRepository.findAllBy(pageable);
        }
        if (list.contains(action)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<ActionBean> actionPojoList = ActionMapper.mapOperatorEntityToPojo(list);
        basePojo = calculatePagination(basePojo, actionPojoList.size());
        basePojo.setList(actionPojoList);
        return basePojo;
    }

    @Transactional
    public ColumnTypes saveColumnType(ColumnTypeBean columnTypePojo) {
        ColumnTypes columnType = ColumnTypeMapper.mapColumnTypePojoToEntity(columnTypePojo);
//        if (columnTypePojo.getId() != null) {
//            columnType.setId(columnTypePojo.getId());
//
//        }
        columnTypeRepository.save(columnType);
        return columnType;

    }

    @Transactional
    public Position savePosition(PositionBean positionPojo) {
        Position position = PositionMapper.mapActionPojoToEnity(positionPojo);
        if (position.getId() != null) {
            position.setId(positionPojo.getId());
        }
        positionRepository.save(position);
        return position;
    }

    @Transactional
    public List<PositionBean> getPositionList() {
        List<Position> positions = (List<Position>) positionRepository.findAllByStatus("Active");
        List<PositionBean> positionPojoList = ObjectMapperUtils.mapAll(positions, PositionBean.class);
        return positionPojoList;
    }

    @Transactional
    public PktFields SavePktFields(PktFieldsBean pktFieldsPojo) {
        PktFields pktFields = null;
        if (pktFieldsPojo.getId() != null) {
            pktFields = pktFieldsRepository.findByFieldNameAndGroupOfAndTableNameAndIdNotIn(pktFieldsPojo.getFieldName(), pktFieldsPojo.getGroupOf(), pktFieldsPojo.getTableName(), pktFieldsPojo.getId());
        } else {
            pktFields = pktFieldsRepository.findByFieldNameAndGroupOfAndTableName(pktFieldsPojo.getFieldName(), pktFieldsPojo.getGroupOf(), pktFieldsPojo.getTableName());
        }
        if (pktFields == null) {
            pktFields = PktFieldsMapper.mapPktFieldPojoToEnity(pktFieldsPojo);
            pktFieldsRepository.save(pktFields);
            return pktFields;
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktFieldsBean> permissionfieldList() {
        List<PktFieldsBean> pkt = new ArrayList<>();
        List<PktFields> pktFields = (List<PktFields>) pktFieldsRepository.findAll();
        for (PktFields dto : pktFields) {
            PktFieldsBean pojo = new PktFieldsBean();
            pojo.setId(dto.getId());
            pojo.setFieldName(dto.getFieldName());
            pojo.setGroupOf(dto.getGroupOf());
            pojo.setStatus(dto.getStatus());
            pojo.setTableName(dto.getTableName());
            pkt.add(pojo);

        }

        List<PktFieldsBean> pktFieldsPojoList = ObjectMapperUtils.mapAll(pkt, PktFieldsBean.class);
        return pktFieldsPojoList;
    }

    @Transactional
    public Application saveApplication(ApplicationBean applicationPojo) {
        Application application = new Application();
        if (applicationPojo.getId() != null) {
            application = applicationRepository.findByName(applicationPojo.getName());
        } else {
            application = applicationRepository.findByName(applicationPojo.getName());
        }
        if (application == null) {
            application = ApplicationMapper.mapApplicationPojoToEnity(applicationPojo);
            if (application.getId() != null) {
                application.setId(applicationPojo.getId());
            }
            applicationRepository.save(application);
            return application;
        } else {
            return null;
        }
    }

    @Transactional
    public List<ColumnTypeBean> getAllColumnTypeList() {
        List<ColumnTypes> columnTypeList = columnTypeRepository.findAll();
        List<ColumnTypeBean> permissionPojoList = ColumnTypeMapper.mapColumnTypeEntityToPojo(columnTypeList);
        return permissionPojoList;

    }

    @Transactional
    public List<ValidatorBean> getValidatorList() {
        List<Validator> validators = (List<Validator>) validatorRepository.findAllByStatus("Active");
        List<ValidatorBean> validatorPojoList = ObjectMapperUtils.mapAll(validators, ValidatorBean.class);
        return validatorPojoList;
    }

    @Transactional
    public List<OperatorBean> getOperatorList() {
        List<Operators> operator = (List<Operators>) operatorRepository.findAllByStatus("Active");
        List<OperatorBean> operatorpojos = ObjectMapperUtils.mapAll(operator, OperatorBean.class);

        return operatorpojos;
    }

    @Transactional
    public List<ActionBean> getActionList() {
        List<Actions> actions = (List<Actions>) actionRepository.findAllByStatus("Active");
        List<ActionBean> actionPojoList = ObjectMapperUtils.mapAll(actions, ActionBean.class);
        return actionPojoList;
    }

    @Transactional
    public List<PktPermissions> gettableslist() {
        List<PktPermissions> pktPermissionsList = (List<PktPermissions>) pktPermissionRepository.findAllByKeyGroupNull();
//        List<ActionBean> actionPojoList = ObjectMapperUtils.mapAll(actions, ActionBean.class);
        return pktPermissionsList;
    }

    @Transactional
    public List<PktPermissionBean> getPktPermisssionList(String subscription) {
//        List<PktPermission> list1 =(List<PktPermission>) pktRepository.findAllByStatus("Active");
        List<PktPermissions> list1 = (List<PktPermissions>) pktPermissionRepository.findAllByKeyGroupAndKeySubscription(null, subscription);
        List<PktPermissionBean> list = ObjectMapperUtils.mapAll(list1, PktPermissionBean.class);
        return list;
    }

    @Transactional
    public List<String> getOperatorListBasedOnApplication(String subscription) {
        List<String> list = new ArrayList<>();
        List<PktPermissions> list1 = pktPermissionRepository.findDistinctByKeySubscriptionAndOperatorContaining(subscription, "List");
        if (list1 != null) {
            for (PktPermissions pktPermissions : list1) {
                if (!list.contains(pktPermissions.getOperator())) {
                    list.add(pktPermissions.getOperator());
                }
            }
        }
        return list;
    }


    public PktPermissions savetext(PktPermissionBean pktPermissionPojo) {
        PktPermissions pktPermissions = PktPermissionMapper.mapPktPermissionPojoToEnity(pktPermissionPojo);
        pktPermissionRepository.save(pktPermissions);
        return pktPermissions;
    }



    @Transactional
    public List<PktPermissions> getTablesListBasedOnApplication(String subscription) {
        List<PktPermissions> list = pktPermissionRepository.findAllByKeySubscriptionAndKeyGroupNull(subscription);
        return list;
    }

    @Transactional
    public BasePojo getPaginatedfieldList(String status, BasePojo basePojo, String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        List<PktPermissions> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "pktPermissionId"));

        if (basePojo.isLastPage() == true) {
            List<PktPermissions> list1 = new ArrayList<>();

            if (!StringUtils.isEmpty(searchText.trim()) ||
                    (StringUtils.isNotBlank(tablesearchText) ||
                            (StringUtils.isNotBlank(keysearchText) ||
                                    (StringUtils.isNotBlank(columnsearchText) ||
                                            (StringUtils.isNotBlank(operatorsearchText)))))) {
                list1 = getList(searchText, tablesearchText, keysearchText, columnsearchText, operatorsearchText);
            } else {
                list1 = pktPermissionRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        PktPermissions fields = new PktPermissions();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "pktPermissionId"));
        }
        if (!StringUtils.isEmpty(searchText.trim()) || (StringUtils.isNotBlank(tablesearchText) ||
                (StringUtils.isNotBlank(keysearchText) ||
                        (StringUtils.isNotBlank(columnsearchText) ||
                                (StringUtils.isNotBlank(operatorsearchText)))))) {

            fields = getFirstObject(sort, searchText, tablesearchText, keysearchText, columnsearchText, operatorsearchText);
            list = getPaginatedList(pageable, searchText, tablesearchText, keysearchText, columnsearchText, operatorsearchText);
        } else {
            fields = pktPermissionRepository.findFirstByStatusAndKeyGroupNotNull(status, sort);
            list = pktPermissionRepository.findAllByStatusAndKeyGroupNotNull(status, pageable);
        }
        if (fields != null) {
            if (list.contains(fields)) {
                basePojo.setStatus(true);
            } else {
                basePojo.setStatus(false);
            }
            List<PktPermissionBean> pojo = PktPermissionMapper.mapEntityToPojo(list);
            basePojo = calculatePagination(basePojo, pojo.size());
            basePojo.setList(pojo);
            return basePojo;
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktPermissions> getList(String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        List<PktPermissions> list = pktPermissionRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(tablesearchText.trim())) {
                    Predicate predicates = cb.like(root.get("tableName"), "%" + tablesearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(keysearchText.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"), "%" + keysearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(columnsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("columnName"), "%" + columnsearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(operatorsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("operator"), "%" + operatorsearchText.trim() + "%");
                    predicatesList.add(predicates);
                }

                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Transactional
    public PktPermissions getFirstObject(Sort sort, String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        List<PktPermissions> list = pktPermissionRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(tablesearchText.trim())) {
                    Predicate predicates = cb.like(root.get("tableName"), "%" + tablesearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(keysearchText.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"), "%" + keysearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(columnsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("columnName"), "%" + columnsearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(operatorsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("operator"), "%" + operatorsearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                Predicate predicates = cb.isNotNull(root.get("keyGroup"));
                predicatesList.add(predicates);

                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, sort);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktPermissions> getPaginatedList(Pageable pageable, String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        Page<PktPermissions> list = pktPermissionRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(tablesearchText.trim())) {
                    Predicate predicates = cb.like(root.get("tableName"), "%" + tablesearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(keysearchText.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"), "%" + keysearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(columnsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("columnName"), "%" + columnsearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(operatorsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("operator"), "%" + operatorsearchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                Predicate predicates = cb.isNotNull(root.get("keyGroup"));
                predicatesList.add(predicates);
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, pageable);
        if (list.getContent().size() > 0) {
            return list.getContent();
        } else {
            return null;
        }
    }

    //get TableNames in PKTFields, Duplicate check Table Name in PKT permission From PKT Fields
    @Transactional
    public List<PktFieldsBean> getPktTablepermissionMaster(String subscriptionName) {
//        List<PktPermission> permissions=pktRepository.findAllByKeySubscriptionAndKeyGroup(subscriptionName,null);
//        List<String> permisionList=new ArrayList<>();
        List<PktFields> list1 = null;
//        if(permissions.size()==0){
//            for(PktPermission pktPermission:permissions){
//
//                permisionList.add(pktPermission.getKeyName());
//            }
//
//            list1 = sassPktfieldsRepository.findAllByFieldNameOrFieldNameAndGroupOfOrGroupOfAndStatus(null,"",null,"","Active");
//        }
//        else{
//            for(PktPermission pktPermission:permissions){
//
//                permisionList.add(pktPermission.getKeyName());
//            }
//
//        }
        list1 = pktFieldsRepository.findAllByFieldNameOrFieldNameAndGroupOfOrGroupOfAndStatus(null, "", null, "", "Active");
        List<PktFieldsBean> list = ObjectMapperUtils.mapAll(list1, PktFieldsBean.class);
        return list;
    }

    @Transactional
    public List<PktFieldsBean> getPktFieldspermissionMaster(String tableName) {
        List<PktFields> list1 = pktFieldsRepository.findAllByTableNameAndStatusAndFieldNameNotNull(tableName, "Active");
        List<PktFieldsBean> list = ObjectMapperUtils.mapAll(list1, PktFieldsBean.class);
        return list;
    }



    @Transactional
    public List<PktFieldsBean> getPktFieldspermissionsMasters(String tableName) {
        List<PktFields> list1 = pktFieldsRepository.findAllByTableNameAndStatusAndFieldNameNotNull(tableName, "Active");
        List<PktFieldsBean> list = ObjectMapperUtils.mapAll(list1, PktFieldsBean.class);
        return list;
    }

    @Transactional
    public PktPermissions saveSearch(String tablecode, List<PktPermissionPojo> searchvalues) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByKeyNameAndKeyGroupNull(tablecode);
        Gson json = new Gson();
        List<String> list = new ArrayList<>();
        for (PktPermissionPojo pktPermissionPojo : searchvalues) {
            list.add(pktPermissionPojo.getColumnName());
        }
        String values = json.toJson(list);
        pktPermissions.setSearchKey(values);
        pktPermissionRepository.save(pktPermissions);
        return pktPermissions;
    }

    @Transactional
    public PktUser savePktUser(PktUserPojo pktUserPojo) {
        PktUser pktUser = new PktUser();
        if (pktUserPojo.getId() != null) {
            pktUser = pktUserRepository.findAllByUserNameAndIdNotIn(pktUserPojo.getUserName(), pktUserPojo.getId());
        } else {
            pktUser = pktUserRepository.findAllByUserName(pktUserPojo.getUserName());
        }
        if (pktUser == null) {
            pktUser = PktUserMapper.mapPktuserPojoToEnity(pktUserPojo);
            if (pktUser.getId() != null) {
                pktUser.setId(pktUserPojo.getId());
            }
            pktUserRepository.save(pktUser);
            return pktUser;
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktFieldsBean> getPlusKeyList(String name){
       List<PktFields> pktFieldsList = pktFieldsRepository.findAllByTableNameAndStatusAndFieldNameNotNull(name,"Active");
      List<PktFieldsBean> list = ObjectMapperUtils.mapAll(pktFieldsList,PktFieldsBean.class);
        return list;
    }

    @Transactional
    public List<PktPermissionPojo> savepluskey(Long id,List<PktPermissionPojo>stringList){
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        List<String> stringList1 = new ArrayList<>();
        for(PktPermissionPojo pktPermissionPojo:stringList){
            stringList1.add(pktPermissionPojo.getColumnName());
        }

        var String = gson.toJson(stringList1);
        pktPermissions.setPlusKey(String);
        pktPermissionRepository.save(pktPermissions);
        return stringList;
    }


    @Transactional
    public List<ConditionPojo> saveCondition(Long id,List<ConditionPojo> conditionPojoList){
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson json = new Gson();
        var String = json.toJson(conditionPojoList);
        pktPermissions.setConditionKey(String);
        pktPermissionRepository.save(pktPermissions);
        return conditionPojoList;
    }

    @Transactional
    public PktPermissions saveDropdown(Long id, List<TablePojo> tablecolumn) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson json = new Gson();
        String values = json.toJson(tablecolumn);
        pktPermissions.setDropdownKey(values);
        pktPermissionRepository.save(pktPermissions);
        return pktPermissions;
    }

    @Transactional
    public PktPermissions saveCalculation(Long id, List<String> calStringList) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson json = new Gson();
        String values = json.toJson(calStringList);
        pktPermissions.setCalcKey(values);
        pktPermissionRepository.save(pktPermissions);
        return pktPermissions;
    }

    public List<PktPermissionPojo> editSearch(Long id) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        List<String> resultlist = new ArrayList<>();
        List<PktPermissionPojo> permissionPojoList = new ArrayList<>();
        Type type = new TypeToken<List<String>>() {}.getType();
        resultlist = gson.fromJson(pktPermissions.getSearchKey(), type);
        if (resultlist != null)
            for (String val : resultlist) {
                PktPermissionPojo pktPermissionPojo = new PktPermissionPojo();
                pktPermissionPojo.setColumnName(val);
                permissionPojoList.add(pktPermissionPojo);
            }
//        String object=json.fromJson(pktPermissions.getSearchValue());
//        pktPermissions.getSearchValue(object);
        return permissionPojoList;
    }


    public PktBuilder editheadervalue(Long id) {
        PktBuilder pktBuilder = pktBuilderRepository.findByPktBuilderId(id);
        return pktBuilder;
    }

    public PktBuilder editFooterValue(Long id) {
        PktBuilder pktBuilder = pktBuilderRepository.findByPktBuilderId(id);
        return pktBuilder;
    }

    public List<TablePojo> editDropdown(Long id) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        List<TablePojo> tablePojoList = new ArrayList<>();
        Type type = new TypeToken<List<TablePojo>>() {
        }.getType();
        tablePojoList = gson.fromJson(pktPermissions.getDropdownKey(), type);
        return tablePojoList;
    }

    public List<String> editCalcualtion(Long id) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        List<String> stringList = new ArrayList<>();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        stringList = gson.fromJson(pktPermissions.getCalcKey(), type);
        return stringList;
    }
    public List<ConditionPojo> editCondition(Long id){
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        List<ConditionPojo>  conditionPojoList = new ArrayList<>();
        Type type = new TypeToken<List<ConditionPojo>>(){}.getType();
        conditionPojoList =gson.fromJson(pktPermissions.getConditionKey(),type) ;
        return conditionPojoList;
    }
    public List<String> editPlus(Long id){
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        List<String>  stringList = new ArrayList<>();
        Type type = new TypeToken<List<String>>(){}.getType();
        stringList =gson.fromJson(pktPermissions.getPlusKey(),type) ;
        return stringList;
    };


    public List<ConditionPojo> editResult(Long id){
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson json = new Gson();
        List<ConditionPojo> stringList = new ArrayList<>();
        Type type = new TypeToken<List<ConditionPojo>>(){}.getType();
        stringList = json.fromJson(pktPermissions.getConditionKey(),type);
        return stringList;
    }

    //    get PKT fields name List#$%
    @Transactional
    public List<PktFieldsBean> getTableCodeList(String keyValue, String keySubscription) {
//    String keyName = null;
        PktPermissions permissions = pktPermissionRepository.findAllByStatusAndKeyValueAndKeySubscriptionAndKeyGroupIsNull("Active", keyValue, keySubscription);
        if (permissions != null) {
            List<PktFields> pktFields = pktFieldsRepository.findAllByTableNameAndStatusAndFieldNameNotNull(permissions.getKeyName(), "Active");
            List<PktFieldsBean> list = ObjectMapperUtils.mapAll(pktFields, PktFieldsBean.class);
            return list;
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktPermissionPojo> getSubscriptionpermsList(String keyValue, String keySubscription) {
        PktPermissions pktPermission =pktPermissionRepository.findByTableNameAndKeySubscriptionAndKeyGroupIsNull(keyValue,keySubscription);
        List<PktPermissions> permissions = pktPermissionRepository.findByKeyGroupAndKeySubscription(pktPermission.getKeyName(),keySubscription);
        List<PktPermissionPojo> permissionPojos = ObjectMapperUtils.mapAll(permissions, PktPermissionPojo.class);
        return permissionPojos;
    }

    //    get PKT fields name List#$%
    @Transactional
    public List<PktPermissionBean> getTableColumnNamesLists(String keyValue, String keySubscription) {
//    String keyName = null;
        PktPermissions permissions = pktPermissionRepository.findAllByStatusAndKeyValueAndKeySubscriptionAndKeyGroupIsNull("Active", keyValue, keySubscription);
        List<PktPermissions> list = pktPermissionRepository.findAllByKeyGroupAndStatus(permissions.getKeyName(), "Active");
        if (permissions != null) {
            List<PktPermissionBean> list1 = ObjectMapperUtils.mapAll(list, PktPermissionBean.class);
            return list1;
        } else {
            return null;
        }

    }

    @Transactional
    public List<PktFields> getcolumncodesbasedontablename(String table) {
        List<PktFields> pktFields = pktFieldsRepository.findAllByTableName(table);
        return pktFields;
    }


    //Save PKT Permission TableColumnMapping

    @Transactional
    public PktPermissions savePktPermsissionTableColumnMapping(PktPermissionBean pktPermissionPojo) {
        List<PktPermissions> permission = null;
        PktPermissions pktPermission = PktPermissionMapper.mapPktPermissionPojoToEnity(pktPermissionPojo);
        PktPermissions pktPermission12 = new PktPermissions();
        String Subscription = null;
        String Operator = null;
        String TableName = null;
        String ColumnName = null;
        String permissiona = null;
        if (pktPermissionPojo.getPktPermissionId() == null) {
            permission = pktPermissionRepository.findAllByKeySubscriptionAndTableNameAndValueeeAndKeyName(pktPermissionPojo.getKeySubscription(), pktPermissionPojo.getTableName(), pktPermissionPojo.getOperator(), pktPermissionPojo.getKeyName());
            if (permission.size() != 0) {
                return null;
            }
        }

        if (pktPermission.getPktPermissionId() != null) {
            permission = pktPermissionRepository.findAllByKeySubscriptionAndTableNameAndValueeeAndKeyNameAndPktPermissionIdNotIn(pktPermissionPojo.getKeySubscription(), pktPermissionPojo.getTableName(), pktPermissionPojo.getOperator(), pktPermissionPojo.getKeyName(), pktPermissionPojo.getPktPermissionId());
            if (permission.size() != 0) {
                return null;
            }
            pktPermission.setPktPermissionId(pktPermission.getPktPermissionId());

        }
//        List<PktPermissions> pktPermission1 = pktPermissionRepository.findBykeyValue(pktPermission.getTableName());
//        for (PktPermissions pktPermission2 : pktPermission1) {
//            pktPermission.setKeyGroup(pktPermission2.getKeyName());
//
//        }
        String keygroup = null;
        String keyAction = null;
        String keyOp = null;
        if(pktPermissionPojo.getValueee()!=null){
            String tableName=pktPermissionPojo.getTableName();
            try {
                JSONObject jsonObject = new JSONObject(pktPermissionPojo.getValueee());

                java.util.Iterator feildList = jsonObject.keys();
                while (feildList.hasNext()) {
                    String keys = String.valueOf(feildList.next());
                    String values = jsonObject.getString(keys);
                    if (StringUtils.equalsIgnoreCase(values, "true")) {
                        String Operator1 = tableName + " " + keys;
                        keyOp =Operator1;
                        String action = keys + " " + tableName;
                        keyAction = action;
                        pktPermissionPojo.setOperator(Operator1);
                        pktPermissionPojo.setKeyAction(action);
                        PktPermissions pkt = pktPermissionRepository.findByTableNameAndKeySubscriptionAndKeyGroupIsNull(pktPermission.getTableName(), pktPermission.getKeySubscription());
                        pktPermissionPojo.setKeyGroup(pkt.getKeyName());
                         pktPermission12 = PktPermissionMapper.mapPktPermissionPojoToEnity(pktPermissionPojo);
                        pktPermissionRepository.save(pktPermission12);
                        if(!StringUtils.isEmpty(pktPermissionPojo.getOperation())){
                            List<PktPermissions> pktPermissions = pktPermissionRepository.findDistinctByKeySubscriptionAndOperatorContaining(pktPermissionPojo.getKeySubscription(),keyOp);
                            Gson gson = new Gson();
                            List<PktPermissionBean> pktPermissionBeans = new ArrayList<>();
                            PktPermissionBean pktPermissionPojo1 = new PktPermissionBean();
                            pktPermissionPojo1.setKeyName(pktPermissionPojo.getOperation());
                            pktPermissionPojo1.setKeyValue(pktPermissionPojo.getOperation());
                            pktPermissionPojo1.setKeyPlaceHolder(pktPermissionPojo.getOperation());
                            pktPermissionPojo1.setKeyValidation("text");
                            pktPermissionPojo1.setKeyAction(keyAction);
                            pktPermissionPojo1.setKeyGroup(pktPermissions.get(0).getKeyGroup());
                            pktPermissionPojo1.setOperator(keyOp);
                            pktPermissionPojo1.setKeySubscription(pktPermissionPojo.getKeySubscription());
                            pktPermissionPojo1.setStatus("Active");
                            pktPermissionPojo1.setPermission("Visible");
                            pktPermissionPojo1.setSync("yes");
                            pktPermissionPojo1.setColumnName(pktPermissionPojo.getOperation());
                            pktPermissionPojo1.setTableName(pktPermissions.get(0).getKeyName());
                            pktPermissionPojo1.setInputType("Select");
                            pktPermissionBeans.add(pktPermissionPojo1);
                            pktPermission12.setOperation(gson.toJson(pktPermissionBeans));
                            pktPermissionRepository.save(pktPermission12);


                        }
                        Gson json = new Gson();
                        if(!StringUtils.isEmpty(pktPermissionPojo.getCalc_key())){
                            List<String> stringList = new ArrayList<>();
                            List<Map<String,String>> map =new ArrayList<>();
                            Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
                            map = json.fromJson(pktPermissionPojo.getCalc_key(),type);
                            for(Map<String,String> mapVal:map){
                                String calcOp = null;
                                calcOp = mapVal.get("cal_op_1") + mapVal.get("operation") +mapVal.get("cal_op_2") + "=" + mapVal.get("cal_res") ;
                                stringList.add(calcOp);
                            }
                            pktPermission12.setCalcKey(json.toJson(stringList));
                            pktPermissionRepository.save(pktPermission12);
//[{"cal_op_1":"dddddddd","operation":"","cal_op_2":"dddddddd","cal_res":"dddddddd"}]
//
//["MM01L1 * MM01S3 \u003d MM01S3","MM01S4 + MM01S8 \u003d MM01S8"]

                        }
                        if(!StringUtils.isEmpty(pktPermissionPojo.getLogicalopKey())){

                            List<ConditionPojo> stringList = new ArrayList<>();
                            List<Map<String,String>> map =new ArrayList<>();
                            Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
                            map = json.fromJson(pktPermissionPojo.getLogicalopKey(),type);
                            for(Map<String,String> mapVal:map){
                                ConditionPojo conditionPojo = new ConditionPojo();
                                String calcOp = null;
                                calcOp = mapVal.get("operand1") + mapVal.get("operation1") +mapVal.get("operand2") ;
                                conditionPojo.setOperation(calcOp);
                                List<Map<String,String>> maps =new ArrayList<>();
                                Type types = new TypeToken<List<Map<String,String>>>(){}.getType();
                                maps = json.fromJson(mapVal.get("resultlogic"),types);
                                List<String> opList = new ArrayList<>();
                                for(Map<String,String> mapVals:maps){
                                    String op = null;
                                    op = mapVals.get("operand1Pop") + mapVals.get("operationPop") +mapVals.get("operand2Pop")+"=" +mapVals.get("resultPop");
                                    opList.add(op);
                                }
                                conditionPojo.setCondition(opList.toString());
                                stringList.add(conditionPojo);
                            }
                            pktPermission12.setConditionKey(json.toJson(stringList));
                            pktPermissionRepository.save(pktPermission12);

//[{"operand1":"aaaaaaaaaaa",
// "operation1":"+","operand2":"aaaaaaaaaaa",
// "resultlogic":"[
// {\"operand1Pop\":\"aaaaaaaaaaa\",\"operationPop\":\"+\",\"operand2Pop\":\"dddddddd\",\
// "resultPop\":\"dddddddd\"}]"}]
//
//
//[{"Condition":"MM01S1 \u003c MM01S2","Operation":"[\"MM01S2 - MM01S6 \u003d MM01S4\"]"},{"Condition":"MM01S2 \u003c MM01S3","Operation":"[\"MM01S2 - MM01S3 \u003d MM01S4\"]"}]




                        }
                        if(!StringUtils.isEmpty(pktPermissionPojo.getDropdownkey())){
                            List<Map<String,String>> mapList = new ArrayList<>();
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Map<String,String>>>() {}.getType();
                            mapList = gson.fromJson(pktPermissionPojo.getDropdownkey(), type);
                            List<TablePojo> table = new ArrayList<>();
                            for(Map<String,String> ob:mapList){
                                TablePojo tablePojo = new TablePojo();
                                tablePojo.setTable(ob.get("dropDownTab"));
                                tablePojo.setFromcolumn(ob.get("fromDropDown"));
                                tablePojo.setComparecolumn(ob.get("compareDropDown"));
                                table.add(tablePojo);
                            }
                            pktPermission12.setDropdownKey(json.toJson(table));
                            pktPermissionRepository.save(pktPermission12);

                        }



                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }


        }


        return pktPermission;
    }

    //    get PKT fields name List#$%
    @Transactional
    public List<PktFieldsBean> getTableCodeListOperation(String keyValue, String keySubscription) {
//    String keyName = null;
        PktPermissions permissions = pktPermissionRepository.findAllByStatusAndKeyValueAndKeySubscriptionAndKeyGroupIsNull("Active", keyValue, keySubscription);
        if (permissions != null) {
            List<PktFields> pktFields = pktFieldsRepository.findAllByTableNameAndStatusAndFieldNameNotNull(permissions.getKeyName(), "Active");
            List<PktFieldsBean> list = ObjectMapperUtils.mapAll(pktFields, PktFieldsBean.class);
            return list;
        } else {
            return null;
        }

    }


    @Transactional
    public PktBuilder editMenuBuilder(Long id) {
        PktBuilder pktBuilder = pktBuilderRepository.findByPktBuilderId(id);

        return pktBuilder;
    }

    //    saveOperationJsonDatas#$%
    @Transactional
    public PktPermissions saveOperationJsonDatas(PktPermissionBean pktPermissionBean) {
        PktPermissions pktPermission = pktPermissionRepository.findOne(pktPermissionBean.getPktPermissionId());
        Gson gson = new Gson();
        List<PktPermissionBean> resultSet = new ArrayList<>();
        Type listType = new TypeToken<List<PktPermissionBean>>() {
        }.getType();
        resultSet = gson.fromJson(pktPermissionBean.getOperation(), listType);
        for (PktPermissionBean pktPermissionBean1 : resultSet) {
            PktPermissions pktPermission1 = pktPermissionRepository.findByTableNameAndKeySubscriptionAndKeyGroupIsNull(pktPermissionBean1.getTableName(), pktPermissionBean1.getKeySubscription());
            pktPermissionBean1.setKeyGroup(pktPermission1.getKeyName());
        }
        String val = gson.toJson(resultSet);
        pktPermission.setOperation(val);
        pktPermissionRepository.save(pktPermission);
        return pktPermission;
    }

    @Transactional
    public List<PktFieldsBean> getAllPktFieldsList() {
        List<PktFields> pktFields = pktFieldsRepository.findAll();
        List<PktFieldsBean> pktFieldsBeans = ObjectMapperUtils.mapAll(pktFields, PktFieldsBean.class);
        return pktFieldsBeans;
    }

    //GetAll Permission TO PKT
    @Transactional
    public List<PktPermissions> getPktPermissionList(String jsonString) throws JSONException {
//        JSONObject jsonObject = new JSONObject(jsonString);
//        String value = jsonObject.get("subscription").toString();
        List<PktPermissions> pktPermissionList = pktPermissionRepository.findAllByKeySubscription(jsonString);
        List<PktPermissions> website = pktPermissionRepository.findAllByKeySubscriptionAndKeyGroupNull(jsonString);
        List<PktPermissionBean> list = ObjectMapperUtils.mapAll(pktPermissionList, PktPermissionBean.class);
        List<PktPermissionBean> weblist = ObjectMapperUtils.mapAll(website, PktPermissionBean.class);
        createWebsitePermissions(weblist,jsonString);
        return pktPermissionList;
    }
    public void createWebsitePermissions(List<PktPermissionBean> list,String subscriptionName){
        Map<String,List<String>> map= new HashMap<>();
        for(PktPermissionBean permissionPojo:list){
            List<PktPermissions> pktPermission = pktPermissionRepository.findAllByKeyGroup(permissionPojo.getKeyName());
            List<String> columns = new ArrayList<>();
            for(PktPermissions permissions:pktPermission){
                if(!columns.contains(permissions.getKeyName()))
                columns.add(permissions.getKeyName());
            }
            map.put(permissionPojo.getKeyName(),columns);
        }
        Gson json = new Gson();
        for(Map.Entry<String,List<String>> m:map.entrySet()){
            WebsitePermissions websitePermissions = new WebsitePermissions();
            websitePermissions = websitePermissionsRepository.findAllByTableNameAndApplicationId(m.getKey(),subscriptionName);
            if(websitePermissions == null){
                websitePermissions = new WebsitePermissions();
                websitePermissions.setTableName(m.getKey());
                websitePermissions.setColFields(json.toJson(m.getValue()));
                websitePermissions.setApplicationId(subscriptionName);
                websitePermissionsRepository.save(websitePermissions);
            }else{
                websitePermissions.setWebsiteId(websitePermissions.getWebsiteId());
                websitePermissions.setTableName(m.getKey());
                websitePermissions.setColFields(json.toJson(m.getValue()));
                websitePermissions.setApplicationId(subscriptionName);
                websitePermissionsRepository.save(websitePermissions);
            }

        }

    }

    @Transactional
    public List<PktPermissions> getAllPktPermissionList() throws JSONException {
        List<PktPermissions> pktPermissionList = pktPermissionRepository.findAll();
//        List<PktPermissionBean> list = ObjectMapperUtils.mapAll(pktPermissionList, PktPermissionBean.class);
        return pktPermissionList;
    }

    //GetAll Permission TO PKT
    @Transactional
    public List<PktBuilder> getpktBuilderList(String jsonString) throws JSONException {
        List<PktBuilder> pktPermissionList = pktBuilderRepository.findAllByApplicationName(jsonString);
        List<PktBuilderBean> list = ObjectMapperUtils.mapAll(pktPermissionList, PktBuilderBean.class);
        return pktPermissionList;
    }

    @Transactional
    public PktBuilder saveMenuBuilderData(PktBuilderBean pktBuilderBean) {
        List<PktBuilder> pktBuilderList = new ArrayList<>();
        if (pktBuilderBean.getPktBuilderId() != null) {
            pktBuilderList = pktBuilderRepository.findAllByApplicationNameAndPktBuilderIdNotIn(pktBuilderBean.getApplicationName(), pktBuilderBean.getPktBuilderId());
        } else {
            pktBuilderList = pktBuilderRepository.findAllByApplicationName(pktBuilderBean.getApplicationName());
        }
        if (pktBuilderList.size() == 0) {
            PktBuilder pktBuilder = PktBuilderMapper.mapPktBuilderBeanToEntity(pktBuilderBean);
            pktBuilderRepository.save(pktBuilder);
            return pktBuilder;
        } else {
            return null;
        }
    }

    @Transactional
    public List<PktBuilderBean> getMenuBuilderList() {
        List<PktBuilder> pktBuilders = pktBuilderRepository.findAll();
        List<PktBuilderBean> pktBuilderBeanList = PktBuilderMapper.mapPktEntityToBuilderBean(pktBuilders);
        return pktBuilderBeanList;

    }

    @Transactional
    public List<Map<String,String>> getMenuListBasedOnApplication(String name) {
        PktBuilder pktBuilders = pktBuilderRepository.findByApplicationName(name);
        Gson gson = new Gson();
        List<Map<String,String>>  list = new ArrayList<>();
        Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
        if (pktBuilders!=null) {
            list = gson.fromJson(pktBuilders.getBuilderValue(), type);
        }
        return list;
    }


    @Transactional
    public List<MenuPojo> getMenuSubMenuListBasedOnApp(String name) {
        PktBuilder pktBuilders = pktBuilderRepository.findByApplicationName(name);
        Gson gson = new Gson();
        List<MenuPojo>  menuPojoList = new ArrayList<>();
        List<Map<String,String>>  list = new ArrayList<>();
        Type type = new TypeToken<List<Map<String,String>>>(){}.getType();
        if (pktBuilders!=null) {
            list = gson.fromJson(pktBuilders.getBuilderValue(), type);
        }
        for (Map<String,String> map:list){
            List<MenuPojo>  subMenuPojoList = new ArrayList<>();
            MenuPojo menuPojo1 = new MenuPojo();
            menuPojo1.setMenuName(map.get("menuName"));
            List<PktPermissions> pktPermissions=pktPermissionRepository.findAllByKeySubscriptionAndMenuKey(name,map.get("menuName"));
            for(PktPermissions pktPermissions1:pktPermissions){
                MenuPojo menuPojo=new MenuPojo();
                menuPojo.setMenuName(pktPermissions1.getTableName());
                subMenuPojoList.add(menuPojo);
            }
            menuPojo1.setSunMenuList(subMenuPojoList);
            menuPojoList.add(menuPojo1);
        }
        return menuPojoList;
    }

    @Transactional
    public List<CreateTablePojo> saveCreatedfields(Long id,List<CreateTablePojo> createTablePojoList) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        var pkt = gson.toJson(createTablePojoList);
        pktPermissions.setCreateKeys(pkt);

        pktPermissionRepository.save(pktPermissions);
        return createTablePojoList;
    };

    @Transactional
    public List<UpdateTablePojo> saveUpdateFields(Long id, List<UpdateTablePojo> updateTablePojoList) {
        PktPermissions pktPermissions = pktPermissionRepository.findAllByPktPermissionId(id);
        Gson gson = new Gson();
        var pkt = gson.toJson(updateTablePojoList);
        pktPermissions.setUpdateKey(pkt);
        pktPermissionRepository.save(pktPermissions);
        return updateTablePojoList;
    };

    @Transactional
    public PktBuilder saveHeaderValues(PktBuilderBean pktBuilderBean) {
        PktBuilder pktBuilders = pktBuilderRepository.findByApplicationNameAndPktBuilderId(pktBuilderBean.getApplicationName(), pktBuilderBean.getPktBuilderId());
        pktBuilders.setHeaderValue(pktBuilderBean.getHeaderValue());
        pktBuilders.setStatus("Active");
        pktBuilderRepository.save(pktBuilders);
        return pktBuilders;

    };

    @Transactional
    public PktBuilder saveFooterValues(PktBuilderBean pktBuilderBean) {
        PktBuilder pktBuilders = pktBuilderRepository.findByApplicationNameAndPktBuilderId(pktBuilderBean.getApplicationName(),pktBuilderBean.getPktBuilderId());
        pktBuilders.setFooterValue(pktBuilderBean.getFooterValue());
        pktBuilders.setStatus("Active");
        pktBuilderRepository.save(pktBuilders);
        return pktBuilders;

    }

    @Transactional
    public List<PktPermissionBean> getPktFieldspermissions(String tableName,String application) {
        List<PktPermissions> list1 = pktPermissionRepository.findByKeyGroupNotNullAndTableNameAndKeySubscriptionAndOperatorContaining(tableName, application,"Save");
        List<PktPermissionBean> list = ObjectMapperUtils.mapAll(list1, PktPermissionBean.class);
        return list;
    }

    @Transactional
    public void saveUpdateFields(String app,String table,String upMultiList) {
        List<Map<String,String>> mapList = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String,String>>>() {}.getType();
        mapList = gson.fromJson(upMultiList, type);

        List<UpdateTablePojo> updateTablePojoList = new ArrayList<>();
        for(Map<String,String> ob:mapList){
            UpdateTablePojo updateTablePojo = new UpdateTablePojo();
            updateTablePojo.setFromtable(ob.get("upFromTab"));
            updateTablePojo.setFromcolumn(ob.get("upFromCol"));
            updateTablePojo.setFromcommoncolumn(ob.get("upFromRel"));
            updateTablePojo.setTotable(ob.get("upToTab"));
            updateTablePojo.setTocolumn(ob.get("upToCo"));
            updateTablePojo.setTocommoncolumn(ob.get("upToRe"));
            updateTablePojo.setOperation(ob.get("upOpe"));
            updateTablePojoList.add(updateTablePojo);
        }
        PktPermissions pktPermissions = pktPermissionRepository.findByKeyGroupNullAndKeyNameAndKeySubscription(table,app);
        var pkt = gson.toJson(updateTablePojoList);
        pktPermissions.setUpdateKey(pkt);
        pktPermissionRepository.save(pktPermissions);
    }

    @Transactional
    public void saveSearchdrag(String app,String table,String searchvalues) {
        Gson gson=new Gson();
        List<String> resultList=new ArrayList<>();
        Type type=new TypeToken<List<Map<String,String>>>(){}.getType();
        List<Map<String,String>> result=gson.fromJson(searchvalues,type);
        for(Map<String,String> map:result){
            resultList.add(map.get("searchNew"));
        }

        PktPermissions pktPermissions = pktPermissionRepository.findByKeyGroupNullAndKeyNameAndKeySubscription(table,app);
        pktPermissions.setSearchKey(gson.toJson(resultList));
        pktPermissionRepository.save(pktPermissions);
    }

    public void savepluspopup(String app,String table,String plusList) {
        Gson gson=new Gson();
        List<String> resultList=new ArrayList<>();
        Type type=new TypeToken<List<Map<String,String>>>(){}.getType();
        List<Map<String,String>> result=gson.fromJson(plusList,type);
        for(Map<String,String> map:result){
            resultList.add(map.get("AddNewPlus"));
        }
        PktPermissions pktPermissions = pktPermissionRepository.findByKeyGroupNullAndKeyNameAndKeySubscription(table,app);
        pktPermissions.setPlusKey(gson.toJson(resultList));
        pktPermissionRepository.save(pktPermissions);
    }


}
