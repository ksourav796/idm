package com.hyva.idm.sass.sassservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hyva.idm.hipro.hiproentity.HiproDump;
import com.hyva.idm.icitem.IcItem;
import com.hyva.idm.icitem.IcItemRepository;
import com.hyva.idm.icitem.IcItempojo;
import com.hyva.idm.pkt.pktBeans.PktPermissionBean;
import com.hyva.idm.pkt.pktRelations.PktBuilder;
import com.hyva.idm.pkt.pktRelations.PktPermissions;
import com.hyva.idm.pkt.pktRepositories.PktBuilderRepository;
import com.hyva.idm.pkt.pktRepositories.PktPermissionsRepository;
import com.hyva.idm.sass.sassentities.*;
import com.hyva.idm.sass.sassentities.EmailServer;
import com.hyva.idm.sass.sassmapper.*;
import com.hyva.idm.sass.sasspojo.*;
import com.hyva.idm.sass.sassrespositories.*;
import com.hyva.idm.sass.sassutil.ObjectMapperUtils;
import com.hyva.idm.util.FileSystemOperations;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import javax.imageio.stream.FileImageInputStream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.hyva.idm.defaultPermissions.defaultPermissions;
import com.hyva.idm.defaultPermissions.defaultPermissionsRepository;
import org.springframework.transaction.annotation.Transactional;




@Component
public class SassControlPanelService {

    @Autowired
    IcItemRepository icItemRepository;
    @Autowired
    SassCountryRepository sassCountryRepository;
    @Autowired
    SassCurrencyRepository sassCurrencyRepository;
    @Autowired
    SassPaymentMethodRepository sassPaymentMethodRepository;
    @Autowired
    SassVersinControlRepository sassVersinControlRepository;
    @Autowired
    SassSubscriptionRepository sassSubscriptionRepository;
    @Autowired
    SassPackageRepository sassPackageRepository;
    @Autowired
    SassPermissionMasterRepository sassPermissionMasterRepository;
    @Autowired
    SassCustomerRepository sassCustomerRepository;
    @Autowired
    HiprodumpRepository hiprodumpRepository;
    @Autowired
    OrderAppPermissionRepository orderAppPermissionRepository;
    @Autowired
    OrderAppSubformRepository orderAppSubformRepository;
    @Autowired
    SaasStateRepository saasStateRepository;
    @Autowired
    PosPaymentTypesRepository posPaymentTypesRepository;
    @Autowired
    SassAddOnRepository sassAddOnRepository;
    @Autowired
    PractitionerRegistrationRepository PractitionerRegistrationRepository;
    @Autowired
    PractitionerordersRepository PractitionerordersRepository;
    @Autowired
    CartRegistrationRepository cartRegistrationRepository;
    @Autowired
    CartCustomerRepository cartCustomerRepository;
    @Autowired
    SassCustomerNotificationsRepository SassCustomerNotificationsRepository;
    @Autowired
    SaasTranscationsDataRepository SaasTranscationsDataRepository;
    @Autowired
    DestinationTypeRepository DestinationTypeRepository;
    @Autowired
    DestinationMapRepository DestinationMapRepository;
    @Autowired
    RTRSyncSettingsRepository RTRSyncSettingsRepository;
    @Autowired
    EmailReaderRepository emailReaderRepository;
    @Autowired
    SassPermissionGroupRepository sassPermissionGroupRepository;
    @Autowired
    PktRepository pktRepository ;
    @Autowired
    SassPktfieldsRepository sassPktfieldsRepository ;
    @Autowired
    OperatorRepository operatorRepository ;
    @Autowired
    ValidatorRepository validatorRepository ;
    @Autowired
    ActionRepository actionRepository ;
    @Autowired
    ApplicationRepository applicationRepository ;
    @Autowired
    PositionRepository positionRepository ;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    ColumnTypeRepository columnTypeRepository;
    @Autowired
    SassOrdersRepository sassOrdersRepository;
    @Autowired
    SassOrdersService sassOrdersService;
    @Autowired
    TicketsRepository ticketsRepository;
    @Autowired
    SassUserRepository sassUserRepository;
    @Autowired
    SassCompanyRepository sassCompanyRepository;
    @Autowired
    EmailServerRepository emailServerRepository;

    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    RenewRepository renewRepository;
    @Autowired
    IdmPermissionRepository idmPermissionRepository;
    @Autowired
    PktPermissionsRepository pktPermissionsRepository;
    @Autowired
    defaultPermissionsRepository defaultPermissionsRepository;
    @Autowired
    PktBuilderRepository pktbuilderRepository;
    @Autowired
            webPermissionRepository webPermissionRepository;
     int paginatedConstants=5;


    public List<SassCurrencyPojo> CurrencyList() {
        List<SassCurrency> currency = (List<SassCurrency>) sassCurrencyRepository.findAll();
//

        List<SassCurrencyPojo> sassCurrencies=new ArrayList<>();
        for(SassCurrency currency1:currency){
            SassCurrencyPojo currencyPojo=new SassCurrencyPojo();
            currencyPojo.setCurrencyCode(currency1.getCurrencyCode());
            currencyPojo.setCurrencyName(currency1.getCurrencyName());
            currencyPojo.setCurrencySymbol(currency1.getCurrencySymbol());
            currencyPojo.setCurrencyId(currency1.getCurrencyId());
            currencyPojo.setCurrencyDescription(currency1.getCurrencyDescription());
            currencyPojo.setStatus(currency1.getStatus());

            if(currency1.getSassCountryId()!=null)
            currencyPojo.setCountry(currency1.getSassCountryId().getCountryName().toString());

            sassCurrencies.add(currencyPojo);
        }

        return sassCurrencies;
    }

    public List<SassPaymentMethodPojo> PaymentMethodList() {
        List<SassPaymentMethod> paymentMethod = (List<SassPaymentMethod>) sassPaymentMethodRepository.findAll();
        List<SassPaymentMethodPojo> sassPaymentMethodPojoList = ObjectMapperUtils.mapAll(paymentMethod, SassPaymentMethodPojo.class);
        return sassPaymentMethodPojoList;
    }

    public List<IcItempojo> IcItemMethodList() {
        List<IcItem> icItem = (List<IcItem>) icItemRepository.findAll();
        List<IcItempojo> icItempojosList = ObjectMapperUtils.mapAll(icItem, IcItempojo.class);
        return icItempojosList;
    }

    public List<PropertyPojo> PropertyList() {
        List<Property> propertyList = (List<Property>) propertyRepository.findAll();
        List<PropertyPojo> propertyPojoList = ObjectMapperUtils.mapAll(propertyList, PropertyPojo.class);
        for(PropertyPojo pr:propertyPojoList){
            if(!StringUtils.isEmpty(pr.getFile()))
                if (!pr.getFile().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + pr.getId() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    pr.setFile(imageLocation);
                }
        }
        return propertyPojoList;
    }
    public List<PropertyPojo> editProperty(String name) {
        List<Property> propertyList = propertyRepository.findAllByName(name);
        List<PropertyPojo> propertyPojoList = ObjectMapperUtils.mapAll(propertyList, PropertyPojo.class);
        for(PropertyPojo pr:propertyPojoList){
            if(!StringUtils.isEmpty(pr.getFile()))
                if (!pr.getFile().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + pr.getName() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    pr.setFile(imageLocation);
                }
        }
        return propertyPojoList;
    }

    public List<SassSubscriptionsPojo> getSubscriptionListbasedonUser(String email){
        SassCustomer sassCustomer = sassCustomerRepository.findByEmail(email);
        List<SassOrders> sassOrdersList = sassOrdersRepository.findBySassCustomer(sassCustomer.getCustomerId());
        if(sassOrdersList.size()>0) {
            for (SassOrders sassOrders : sassOrdersList) {
                SassSubscriptionsPojo sassSubscriptionsPojo = new SassSubscriptionsPojo();
                sassSubscriptionsPojo.setEmail( sassOrders.getCompanyEmail() );
                sassSubscriptionsPojo.setUsername( sassOrders.getUsername() );
                sassSubscriptionsPojo.setOrderNumber( sassOrders.getOrderNumber() );
                sassSubscriptionsPojo.setPassword( sassOrders.getPassword() );
                sassSubscriptionsPojo.setIncorpDate( sassOrders.getIncorpDate() );
                sassSubscriptionsPojo.setKey( sassOrders.getLicenceKey() );
                if(sassOrders.getSassSubscriptionsId()!=null)
                sassSubscriptionsPojo.setAppUrl( sassOrders.getSassSubscriptionsId().getAppUrl() );
                sassSubscriptionsPojo.setCompanies( sassOrders.getSassSubscriptionsId().getCompanies() );
                sassSubscriptionsPojo.setRegistrationNo( sassOrders.getCompanyRegistrationNo() );
                sassSubscriptionsPojo.setPhone( sassOrders.getCompanyPhone() );
                sassSubscriptionsPojo.setRegistrationNo( sassOrders.getCompanyGSTno() );
                sassSubscriptionsPojo.setCustomerName( sassOrders.getCustomerName() );
                sassSubscriptionsPojo.setValidity(sassOrders.getSassSubscriptionsId().getValidity());
                sassSubscriptionsPojo.setVersion( sassOrders.getSassSubscriptionsId().getVersion() );
                sassSubscriptionsPojo.setRenewDate(sassOrders.getRenew().getRenewalDate());
                sassSubscriptionsPojo.setSubscriptionId(sassOrders.getSassSubscriptionsId().getSubscriptionId());
                sassSubscriptionsPojo.setRenewValidity(sassOrders.getRenew().getValidity());
                Application application = applicationRepository.findByName( sassOrders.getSassSubscriptionsId().getApplication() );
                    if(application!=null) {
                    sassSubscriptionsPojo.setApplicationId( application.getId().toString() );
                    sassSubscriptionsPojo.setApplicationName(application.getName());
                }
                    sassSubscriptionsPojo.setStatus( sassOrders.getPostingStatus() );
                    sassSubscriptionsPojo.setUserAccountId( sassOrders.getSassOrdersId() );
                    sassSubscriptionsPojo.setAddonPermission( sassOrders.getSassSubscriptionsId().getAddonPermission() );
                    sassSubscriptionsPojo.setCompanyName( sassOrders.getCompanyName() );
                    sassSubscriptionsPojo.setActualPrice( sassOrders.getSassSubscriptionsId().getActualPrice() );
                    sassSubscriptionsPojo.setDeveloperId( sassOrders.getDeveloperId() );
                    sassSubscriptionsPojo.setSubscriptionName( sassOrders.getSassSubscriptionsId().getSubscriptionName() );
                    sassSubscriptionsPojo.setSubscriptionFor( sassOrders.getSassSubscriptionsId().getSubscriptionFor() );
                    List<SassSubscriptionsPojo> list = new ArrayList<>();
                    list.add( sassSubscriptionsPojo );
                    return list;
            }
        }
        return null;
    }

    public List<SassCustomerPojo> CustomerList(String search) {
        List<SassCustomer> customer = new ArrayList<>();
        if (StringUtils.isBlank(search)) {
            customer = (List<SassCustomer>) sassCustomerRepository.findAll();
        } else {
            customer = sassCustomerRepository.findAllByEmailStartsWith(search);
        }
        List<SassCustomerPojo> sassCustomerPojos1 = new ArrayList<>();
        for (SassCustomer sassCustomer : customer) {
            SassCustomerPojo sassCustomerPojo = new SassCustomerPojo();
            sassCustomerPojo.setCustomerId(sassCustomer.getCustomerId());
            if (sassCustomer.getStateId() != null)
                sassCustomerPojo.setStateId(sassCustomer.getStateId().getStateName());

          if(sassCustomer.getCountryId()!=null)
           sassCustomerPojo.setCountryId(sassCustomer.getCountryId().getCountryName());
           if(sassCustomer.getCurrencyId()!=null)
            sassCustomerPojo.setCurrencyId(sassCustomer.getCurrencyId().getCurrencyName());
            sassCustomerPojo.setCustomerName(sassCustomer.getCustomerName());
            sassCustomerPojo.setCustomerId(sassCustomer.getCustomerId());
//            sassCustomerPojo.setIFSCCode(sassCustomer.getIFSCCode());
            sassCustomerPojo.setCustomerCode(sassCustomer.getCustomerCode());
            sassCustomerPojo.setBankName(sassCustomer.getBankName());
            sassCustomerPojo.setAccountNo(sassCustomer.getAccountNo());
            sassCustomerPojo.setBranchName(sassCustomer.getBranchName());
            sassCustomerPojo.setPersonIncharge(sassCustomer.getPersonIncharge());
            sassCustomerPojo.setAddress(sassCustomer.getAddress());
            sassCustomerPojo.setGstCode(sassCustomer.getGstCode());
            sassCustomerPojo.setEmail(sassCustomer.getEmail());
            sassCustomerPojo.setCustomerNumber(sassCustomer.getCustomerNumber());
            sassCustomerPojo.setCreditedLimit(sassCustomer.getCreditedLimit());
            sassCustomerPojo.setPanNO(sassCustomer.getPanNo());
            sassCustomerPojo.setWebsite(sassCustomer.getWebsite());
            sassCustomerPojo.setContactPerson(sassCustomer.getContactPerson());
            sassCustomerPojo.setCreditedTerm(sassCustomer.getCreditedTerm());
            sassCustomerPojo.setIfsc(sassCustomer.getIfsc());


            sassCustomerPojos1.add(sassCustomerPojo);
        }
        return sassCustomerPojos1;
    }

    public SassCurrency SaveCurrency(SassCurrencyPojo saveNewCurrencyDetails) {
        SassCurrency sassCurrency = SaasCurrencyMapper.mapPojoToEntity(saveNewCurrencyDetails);
        SassCountry sassCountry=sassCountryRepository.findByCountryName((saveNewCurrencyDetails.getSassCountryPojoId()));
      sassCurrency.setSassCountryId(sassCountry);
//        sassCurrency.setSassCountryId(new SassCountry(Long.parseLong(saveNewCurrencyDetails.getSassCountryPojoId())));
        if (sassCurrency.getCurrencyId() != null) {
            sassCurrency.setCurrencyId(saveNewCurrencyDetails.getCurrencyId());
        }
        sassCurrencyRepository.save(sassCurrency);
        return sassCurrency;
    }


    public SassCustomer SaveCustomer(SassCustomerPojo saveCustomerDetails) {
        SassCustomer sassCustomer = null;
        sassCustomer = sassCustomerRepository.findByEmail(saveCustomerDetails.getEmail());
        if (sassCustomer != null) {
            sassCustomer = null;
        } else {
            sassCustomer = SassCustomerMapper.mapPojoToEntity(saveCustomerDetails);
            if (saveCustomerDetails.getCountryId() != null) {
                SassCountry sassCountry=sassCountryRepository.findByCountryName((saveCustomerDetails.getCountryId()));
                sassCustomer.setCountryId(sassCountry);
            }
            if (saveCustomerDetails.getCurrencyId() != null) {
                SassCurrency currency=sassCurrencyRepository.findByCurrencyName(saveCustomerDetails.getCurrencyId());
                sassCustomer.setCurrencyId(currency);
            }
            if (saveCustomerDetails.getStateId() != null) {
               SaasState state=saasStateRepository.findByStateName(saveCustomerDetails.getStateId());
               sassCustomer.setStateId(state);
            }
            if (sassCustomer.getCustomerId() != null) {
                sassCustomer.setCustomerId(saveCustomerDetails.getCustomerId());
            }
            sassCustomerRepository.save(sassCustomer);
        }
        return sassCustomer;
    }

    public SassCurrencyPojo getCurrencyPrerequisite() {
        SassCurrencyPojo sassCurrencyPojo = new SassCurrencyPojo();
        List<SassCountry> sassCountryList = (List<SassCountry>) sassCountryRepository.findAll();
        List<SassCountryPojo> sassCountryPojoList = ObjectMapperUtils.mapAll(sassCountryList, SassCountryPojo.class);
        sassCurrencyPojo.setSassCountryPojoList(sassCountryPojoList);
        return sassCurrencyPojo;
    }

    public SassPaymentMethod savePaymentMethodList(SassPaymentMethodPojo saveNewPaymentMethodDetails) {
        SassPaymentMethod sassPaymentMethod = SassPayMethodMapper.mapPojoToEntity(saveNewPaymentMethodDetails);
//        if (sassPaymentMethod.getPaymentmethodId() != null) {
//            sassPaymentMethod.setPaymentmethodId(saveNewPaymentMethodDetails.getPaymentmethodId());
//        }
        sassPaymentMethodRepository.save(sassPaymentMethod);
        return sassPaymentMethod;
    }

    public SassCountry SaveCountryDetails(SassCountryPojo sassCountryPojo) {
        SassCountry sassCountry = SaasCountryMapper.mapPojoToEntity(sassCountryPojo);
        if (sassCountry.getCountryId() != null) {
            sassCountry.setCountryId(sassCountryPojo.getCountryId());
        }
        sassCountryRepository.save(sassCountry);
        return sassCountry;
    }

    public Property SaveProperty(PropertyPojo propertyPojo){
        Property property = PropertyMapper.mapPojoToEntity(propertyPojo);
        if (property.getId() != null) {
            property.setId(propertyPojo.getId());
        }
        byte byteArray[];
        String fileName = FileSystemOperations.getImagesDirItem() + File.separator + propertyPojo.getName() + ".png";
        //read item image
        if (!StringUtils.isEmpty(propertyPojo.getFile())) {
            try {
                //decode Base64 String to image
                FileOutputStream fos = new FileOutputStream(fileName);
                FileSystemOperations.deleteImage(property.getFile());
                //remove tag 'data:image/png;base64,'
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(propertyPojo.getFile().split(",")[1]);
                //write to file
                fos.write(byteArray);
                fos.flush();
                fos.close();
                property.setFile(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(propertyPojo.getId()!=null&&StringUtils.isEmpty(propertyPojo.getFile())){
            Property pro=propertyRepository.findOne(propertyPojo.getId());
            property.setFile(pro.getFile());
        }
        propertyRepository.save(property);
        return property;
    }

    public PktFields SavePktFields(PktFieldsPojo pktFieldsPojo) {
        PktFields pktFields = null;
        if (pktFieldsPojo.getId() != null) {
            pktFields = sassPktfieldsRepository.findByFieldNameAndGroupOfAndTableNameAndIdNotIn(pktFieldsPojo.getFieldName(), pktFieldsPojo.getGroupOf(), pktFieldsPojo.getTableName(), pktFieldsPojo.getId());
        } else {
            pktFields = sassPktfieldsRepository.findByFieldNameAndGroupOfAndTableName(pktFieldsPojo.getFieldName(), pktFieldsPojo.getGroupOf(), pktFieldsPojo.getTableName());
        }
        if (pktFields == null) {
            pktFields = PktFieldMapper.mapPktFieldPojoToEnity(pktFieldsPojo);
            sassPktfieldsRepository.save(pktFields);
            return pktFields;
        } else {
            return null;
        }
    }

    public Operator saveOperator(Operatorpojo operatorpojo) {
        Operator operator = null;
        if (operatorpojo.getId() != null) {
            operator = operatorRepository.findByNameAndIdNotIn(operatorpojo.getName(), operatorpojo.getId());
        } else {

            operator = operatorRepository.findByName(operatorpojo.getName());
        }
        if (operator == null) {
            operator = OperatorMapper.mapOperatorPojoToEnity(operatorpojo);
            operatorRepository.save(operator);
            return operator;
        } else {
            return null;
        }
    }
    public Application saveApplication(ApplicationPojo applicationPojo) {
        Application application= new Application();
        if(applicationPojo.getId()!=null){
            application=applicationRepository.findByNameAndIdNot(applicationPojo.getName(),applicationPojo.getId());
        }
        else{
            application=applicationRepository.findByName(applicationPojo.getName());
        }
        if(application==null) {
            application=ApplicationMapper.mapApplicationPojoToEnity(applicationPojo);
            if (application.getId() != null) {
                application.setId(applicationPojo.getId());
            }
            applicationRepository.save(application);
            return application;
        }
        else{
            return null;
        }
    }

    public Validator saveValidator(ValidatorPojo validatorPojo) {
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

    public Position savePosition(PositionPojo positionPojo) {
        Position position = PositionMapper.mapActionPojoToEnity(positionPojo);
        if(position.getId()!=null){
            position.setId(positionPojo.getId());
        }
        positionRepository.save(position);
        return position;
    }

    public SassProjectModule saveVersionControlList(SassProjectModulePojo saveVersionDetails) {
        SassProjectModule sassProjectModule = SassVersionMapper.mapPojoToEntity(saveVersionDetails);
        if (sassProjectModule.getId() != null) {
            sassProjectModule.setId(saveVersionDetails.getId());
        }
        sassVersinControlRepository.save(sassProjectModule);
        return sassProjectModule;
    }

    public List<SassProjectModulePojo> VersionControlList() {
        List<SassProjectModule> projectModules = (List<SassProjectModule>) sassVersinControlRepository.findAll();
        List<SassProjectModulePojo> sassProjectModulePojos = ObjectMapperUtils.mapAll(projectModules, SassProjectModulePojo.class);
        return sassProjectModulePojos;
    }

    public SassSubscriptions saveSubscriptionList(SassSubscriptionsPojo saveSubscriptionDetails) {
        SassSubscriptions sassSubscriptions = new SassSubscriptions();
        SassSubscriptions sassSubscription = new SassSubscriptions();
        if(saveSubscriptionDetails.getSubscriptionId()!=null){
            sassSubscription = sassSubscriptionRepository.findAllBySubscriptionForAndSubscriptionNameAndVersionAndSubscriptionIdNotIn(saveSubscriptionDetails.getSubscriptionFor(),saveSubscriptionDetails.getSubscriptionName(),saveSubscriptionDetails.getVersion(),saveSubscriptionDetails.getSubscriptionId());
        }else {
            sassSubscription = sassSubscriptionRepository.findAllBySubscriptionForAndSubscriptionNameAndVersion(saveSubscriptionDetails.getSubscriptionFor(),saveSubscriptionDetails.getSubscriptionName(),saveSubscriptionDetails.getVersion());
        }
        if(sassSubscription==null) {
            sassSubscriptions = SassSubscriptionMapper.mapPojoToEntity(saveSubscriptionDetails);
            if (saveSubscriptionDetails.getSubscriptionId() != null && saveSubscriptionDetails.getSubscriptionFor().equalsIgnoreCase("HiConnect")) {
                sassSubscriptions.setSubscriptionFor("HiConnect");
            } else if (saveSubscriptionDetails.getSubscriptionId() != null && saveSubscriptionDetails.getSubscriptionFor().equalsIgnoreCase("HiSync")) {
                sassSubscriptions.setHiSync("HiSync");

            } else if (saveSubscriptionDetails.getSubscriptionId() != null && saveSubscriptionDetails.getSubscriptionFor().equalsIgnoreCase("HiAccount")) {
                sassSubscriptions.setHiAccount("HiAccount");
            }
            if (sassSubscriptions.getSubscriptionId() == null || sassSubscriptions.getSubscriptionId().equals("")) {
                sassSubscriptions.setSubscriptionId(saveSubscriptionDetails.getSubscriptionId());
            }

            sassSubscriptionRepository.save(sassSubscriptions);
            return sassSubscriptions;
        }else {
            return null;
        }
    }


    public SassOrders saveOrdersrenew(SassOrdersPojo sassOrdersPojo){
        SassOrders sassOrders = sassOrdersRepository.findAllBySassOrdersId( sassOrdersPojo.getSassOrdersId());
        Renew renew = sassOrders.getRenew();
        Calendar c = Calendar.getInstance();
//        if(renew.getRenewalDate()!=null) {
            c.setTime(sassOrdersPojo.getRenewDate());
//        }else {
//            c.setTime(new Date());
//        }
        int validity =Integer.parseInt(sassOrdersPojo.getValidity());
        c.add(Calendar.DAY_OF_MONTH,+validity);
        Date newDate =c.getTime();
        sassOrders.setExpiryDate(newDate);
//        sassOrders.setExpiryDate(renew.getExpiryDate());
        if(sassOrders.getRenew()!=null)
        renew.setRenewalDate(sassOrdersPojo.getRenewDate());
        renew.setExpiryDate(sassOrders.getExpiryDate());
        renew.setValidity(sassOrdersPojo.getValidity());
        if(sassOrders.getExpiryDate().before(new Date())) {
            renew.setStatus("Expired");
        }else{
            renew.setStatus("Active");
        }
        renew.setSubscriptionName(sassOrders.getSassSubscriptionsId().getSubscriptionName());
        renewRepository.save( renew);
        sassOrdersRepository.save(sassOrders);
    return sassOrders;
    }


    //    public List<SassSubscriptionsPojo>  SubscriptionByName(String  subName) {
//        List<SassSubscriptions> subscriptions = (List<SassSubscriptions>) sassSubscriptionRepository.findBySubscriptionName(subName);
//        List<SassSubscriptionsPojo> sassSubscriptionsPojoList = ObjectMapperUtils.mapAll(subscriptions, SassSubscriptionsPojo.class);
//        return sassSubscriptionsPojoList;
//    }

    public SassSubscriptionsPojo editsubscription(Long id){
        SassSubscriptions sassSubscriptions = sassSubscriptionRepository.findBySubscriptionId(id);
        List<SassSubscriptions> sassSubscriptionsList = new ArrayList<>();
        sassSubscriptionsList.add(sassSubscriptions);
        SassSubscriptionsPojo sassSubscriptionsPojo = SassSubscriptionMapper.mapSubscriptionEntityToPojo(sassSubscriptionsList).get(0);
        return sassSubscriptionsPojo;
    }
    public SassSubscriptionsPojo editemail(Long id){
        SassSubscriptions sassSubscriptions = sassSubscriptionRepository.findBySubscriptionId(id);
        List<SassSubscriptions> sassSubscriptionsList = new ArrayList<>();
        sassSubscriptionsList.add(sassSubscriptions);
        SassSubscriptionsPojo sassSubscriptionsPojo = SassSubscriptionMapper.mapSubscriptionEntityToPojo(sassSubscriptionsList).get(0);
        return sassSubscriptionsPojo;
    }
    public SassSubscriptionsPojo renewsubscription(Long id){
        SassSubscriptions sassSubscriptions = sassSubscriptionRepository.findBySubscriptionId(id);
        List<SassSubscriptions> sassSubscriptionsList = new ArrayList<>();
        sassSubscriptionsList.add(sassSubscriptions);
        SassSubscriptionsPojo sassSubscriptionsPojo = SassSubscriptionMapper.maprenewSubscriptionEntityToPojo(sassSubscriptionsList).get(0);
        return sassSubscriptionsPojo;
    }
    public SassSubscriptions savepublish(Long id,String status){
        SassSubscriptions sassSubscriptions = sassSubscriptionRepository.findBySubscriptionId(id);
        sassSubscriptions.setStatus( status );
        sassSubscriptionRepository.save( sassSubscriptions);
        return sassSubscriptions;
    }

    public List<SassSubscriptionsPojo> SubscriptionList(String type) {
        List<SassSubscriptions> subscriptions=new ArrayList<>(  );
        if(StringUtils.isEmpty( type )) {
           subscriptions =sassSubscriptionRepository.findByStatus("Active");
        }else if(StringUtils.equalsIgnoreCase( type,"NotApplication")){
            subscriptions =sassSubscriptionRepository.findBySubscriptionForIsNotLikeAndStatus("Application","Active");
        }else if(!StringUtils.isEmpty( type)){
            subscriptions =sassSubscriptionRepository.findAllBySubscriptionForAndStatus(type,"Active");
        }
        List<SassSubscriptionsPojo> sassSubscriptionsPojoList =SassSubscriptionMapper.mapSubscriptionEntityToPojo(subscriptions);
        return sassSubscriptionsPojoList;
    }
    public List<SassSubscriptionsPojo> getSubscriptionListByDeveloperId(String developerCode) {
        List<SassSubscriptions> subscriptions = (List<SassSubscriptions>) sassSubscriptionRepository.findByDeveloperId(developerCode);
        List<SassSubscriptionsPojo> sassSubscriptionsPojoList =SassSubscriptionMapper.mapSubscriptionEntityToPojo(subscriptions);
        return sassSubscriptionsPojoList;
    }
    public List<SassOrdersPojo> getAccountList(String developerCode) {
      List<SassOrders> sassOrdersList = sassOrdersRepository.findAllByDeveloperId(developerCode);
      List<SassOrdersPojo> sassOrdersPojoList = SassOrdersMapper.mapEntityToPojo(sassOrdersList);
        return sassOrdersPojoList;
    }

    public BasePojo getPaginatedSubscriptionList(BasePojo basePojo, String searchText) {
        List<SassSubscriptions> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "subscriptionId"));
        if (basePojo.isLastPage() == true) {
            List<SassSubscriptions> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = sassSubscriptionRepository.findAllBySubscriptionNameContaining(searchText);
            } else {
                list1 = sassSubscriptionRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        SassSubscriptions ctgry = new SassSubscriptions();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subscriptionId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            ctgry = sassSubscriptionRepository.findFirstBySubscriptionNameContaining(searchText, sort);
            list = sassSubscriptionRepository.findAllBySubscriptionNameContaining(searchText, pageable);
        } else {
            ctgry = sassSubscriptionRepository.findFirstBy(sort);
            list = sassSubscriptionRepository.findAllBy(pageable);
        }
        if (list.contains(ctgry)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<SassSubscriptionsPojo> subscriptionsPojoList = SassSubscriptionMapper.mapSubscriptionEntityToPojo(list);
        basePojo = calculatePagination(basePojo, subscriptionsPojoList.size());
        basePojo.setList(subscriptionsPojoList);
        return basePojo;
    }


    public List<PermissionMaster> PermissionMasterList() {
        Iterable<PermissionMaster> permissionMaster = sassPermissionMasterRepository.findAll();
        List<PermissionMaster> permasterPojos = new ArrayList<>();
        permissionMaster.forEach(perMasterEntity -> {
            permasterPojos.add(perMasterEntity);

        });
        return permasterPojos;
    }

    public HashMap<Object, Object> retriveCompanyPermissions() {
        HashMap<Object, Object> finalResult = new HashMap<>();
        List<PermissionMaster> queryResult = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", null);
        for (PermissionMaster permission : queryResult) {
            HashMap node1 = new HashMap();
            node1.put("id", permission.getPmId());
            node1.put("permissionString", permission.getPermissionMasterkey());
            node1.put("status", "Active");
            node1.put("children", new HashSet());
            List<PermissionMaster> queryResult1 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", permission);
            Map level1End = new HashMap<>();
            for (PermissionMaster permission1 : queryResult1) {
                HashMap node2 = new HashMap();
                node2.put("id", permission1.getPmId());
                node2.put("permissionString", permission1.getPermissionMasterkey());
                node2.put("status", "Active");
                node2.put("children", new HashSet());
                List<PermissionMaster> queryResult2 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", permission1);
                Map level2End = new HashMap<>();
                for (PermissionMaster permission2 : queryResult2) {
                    HashMap node3 = new HashMap();
                    node3.put("id", permission2.getPmId());
                    node3.put("permissionString", permission2.getPermissionMasterkey());
                    node3.put("status", "Active");
                    node3.put("children", new HashSet());

                    List<PermissionMaster> queryResult3 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", permission2);
                    Map level3End = new HashMap<>();
                    for (PermissionMaster permission3 : queryResult3) {
                        HashMap node4 = new HashMap();
                        node4.put("id", permission3.getPmId());
                        node4.put("permissionString", permission3.getPermissionMasterkey());
                        node4.put("status", "Active");
                        node4.put("children", new HashSet());

                        List<PermissionMaster> queryResult4 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", permission3);
                        Map level4End = new HashMap();
                        for (PermissionMaster permission4 : queryResult4) {
                            HashMap node5 = new HashMap();
                            node5.put("id", permission4.getPmId());
                            node5.put("permissionString", permission4.getPermissionMasterkey());
                            node5.put("status", "Active");
                            node5.put("children", new HashSet());

                            List<PermissionMaster> queryResult5 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", permission4);
                            Map level5End = new HashMap();
                            for (PermissionMaster permission5 : queryResult5) {
                                HashMap node6 = new HashMap();
                                node6.put("id", permission5.getPmId());
                                node6.put("permissionString", permission5.getPermissionMasterkey());
                                node6.put("status", "Active");
                                node6.put("children", new HashSet());

                                level5End.put(permission5.getDescription(), node6);
                                node5.put("children", level5End);
                            }
                            level4End.put(permission4.getDescription(), node5);
                            node4.put("children", level4End);
                        }
                        level3End.put(permission3.getDescription(), node4);
                        node3.put("children", level3End);
                    }
                    level2End.put(permission2.getDescription(), node3);
                    node2.put("children", level2End);
                }
                level1End.put(permission1.getDescription(), node2);
                node1.put("children", level1End);
            }//end level1
            finalResult.put(permission.getDescription(), node1);
        }
        return finalResult;
    }

    public SassPackages savePackageList(SassPackagesPojo savePackageDetails) {
        SassPackages sassPackages = SassPackagesMapper.mapPojoToEntity(savePackageDetails);
        if (sassPackages.getPackagesSASSId() != null) {
            sassPackages.setPackagesSASSId(savePackageDetails.getPackagesSASSId());
        }
        sassPackageRepository.save(sassPackages);
        return sassPackages;
    }

    public List<SassPackagesPojo> PackagesList() {
        List<SassPackages> packages = (List<SassPackages>) sassPackageRepository.findAll();
        List<SassPackagesPojo> sassPackagesPojos = ObjectMapperUtils.mapAll(packages, SassPackagesPojo.class);
        return sassPackagesPojos;
    }

    public List<Operatorpojo> getOperatorList() {
        List<Operator> operator = (List<Operator>) operatorRepository.findAllByStatus("Active");
        List<Operatorpojo> operatorpojos = ObjectMapperUtils.mapAll(operator, Operatorpojo.class);
        return operatorpojos;
    }

    public List<ValidatorPojo> getValidatorList() {
        List<Validator> validators = (List<Validator>) validatorRepository.findAllByStatus("Active");
        List<ValidatorPojo> validatorPojoList = ObjectMapperUtils.mapAll(validators, ValidatorPojo.class);
        return validatorPojoList;
    }

    public List<PositionPojo> getPositionList() {
        List<Position> positions = (List<Position>) positionRepository.findAllByStatus("Active");
        List<PositionPojo> positionPojoList = ObjectMapperUtils.mapAll(positions, PositionPojo.class);
        return positionPojoList;
    }
    public List<ActionPojo> getActionList() {
        List<Action> actions = actionRepository.findAllByStatus("Active");
        List<ActionPojo> actionPojoList = ObjectMapperUtils.mapAll(actions, ActionPojo.class);
        return actionPojoList;
    }
    public List<ApplicationPojo> getApplicationList() {
        List<Application> applications = applicationRepository.findAllByStatus("Active");
        List<ApplicationPojo> applicationPojoList = ObjectMapperUtils.mapAll(applications, ApplicationPojo.class);
        return applicationPojoList;
    }
    public List<ApplicationPojo> getdevApplicationList(String code) {
        List<Application> applications = applicationRepository.findByDeveloperId(code);
        List<ApplicationPojo> applicationPojoList = ApplicationMapper.mapEntityToPojo(applications);
        ApplicationPojo applicationPojo=new ApplicationPojo();
        SassOrders sassOrders=sassOrdersRepository.findByDeveloperId(code);
        applicationPojo.setDeveloperId(code);
        applicationPojo.setName(sassOrders.getSassSubscriptionsId().getApplication());
        applicationPojo.setId(applicationRepository.findByName(applicationPojo.getName()).getId());
        applicationPojoList.add(applicationPojo);
        return applicationPojoList;
    }

    public List<SassCountryPojo> getCountryList() {
        List<SassCountry> country = (List<SassCountry>) sassCountryRepository.findAll();
        List<SassCountryPojo> sassCountryPojos = ObjectMapperUtils.mapAll(country, SassCountryPojo.class);
        return sassCountryPojos;
    }
    public List<RenewPojo> getRenewList() {
        List<RenewPojo> list = new ArrayList<>();
        List<SassOrders> sassOrdersList = sassOrdersRepository.findAll();
        for(SassOrders sassOrders :sassOrdersList) {
            RenewPojo renewPojo = new RenewPojo();
//            List<Renew> renews = (List<Renew>) renewRepository.findAll();
            renewPojo.setCustomerName(sassOrders.getCustomerName());
            renewPojo.setOrderId(sassOrders.getSassOrdersId());
            renewPojo.setExpiryDate(sassOrders.getRenew().getExpiryDate());
            renewPojo.setCreatedDate(sassOrders.getRenew().getCreatedDate());
            renewPojo.setRenewalDate(sassOrders.getRenew().getRenewalDate());
            renewPojo.setValidity(sassOrders.getRenew().getValidity());
            renewPojo.setSubscriptionName(sassOrders.getRenew().getSubscriptionName());
            renewPojo.setId(sassOrders.getRenew().getId());
            list.add(renewPojo);
        }
        List<RenewPojo> renewPojo = ObjectMapperUtils.mapAll(list, RenewPojo.class);

        return renewPojo;
    }

    public List<PktFieldsPojo> permissionfieldList() {
    List<PktFieldsPojo> pkt=new ArrayList<>();
        List<PktFields> pktFields = (List<PktFields>) sassPktfieldsRepository.findAll();
        for(PktFields dto:pktFields){
            PktFieldsPojo pojo=new  PktFieldsPojo();
            pojo.setId(dto.getId());
            pojo.setFieldName(dto.getFieldName());
            pojo.setGroupOf(dto.getGroupOf());
            pojo.setStatus(dto.getStatus());
            pojo.setTableName(dto.getTableName());
            pkt.add(pojo);

        }

        List<PktFieldsPojo> pktFieldsPojoList = ObjectMapperUtils.mapAll(pkt, PktFieldsPojo.class);
        return pktFieldsPojoList;
    }

    public List<SaasStatePojo> stateList() {
        List<SaasState> state = (List<SaasState>) saasStateRepository.findAll();
        List<SaasStatePojo> statePojos=new ArrayList<>();
        for(SaasState state1:state){
            SaasStatePojo saasStatePojo=new SaasStatePojo();
            saasStatePojo.setId(state1.getId());
            saasStatePojo.setStateName(state1.getStateName());
            saasStatePojo.setStatus(state1.getStatus());
            saasStatePojo.setStateCode(state1.getStateCode());
            saasStatePojo.setVehicleSeries(state1.getVehicleSeries());
            if(state1.getCountryId()!=null)
            saasStatePojo.setCountryId(state1.getCountryId().getCountryName().toString());
            statePojos.add(saasStatePojo);


        }
//        List<SaasStatePojo> PojoList = ObjectMapperUtils.mapAll(state, SaasStatePojo.class);
        return statePojos;
    }

    public SaasState SavestateDetails(SaasStatePojo details) {
        SaasState SaasState = SaasStateMapper.mapPojoToEntity(details);
        SassCountry sassCountry=sassCountryRepository.findByCountryName((details.getCountryId()));
        SaasState.setCountryId(sassCountry);
//        SaasState.setCountryId(new SassCountry(Long.parseLong(details.getCountryId())));
        if (SaasState.getId() != null) {
            SaasState.setId(details.getId());
        }
        saasStateRepository.save(SaasState);
        return SaasState;

    }

    public Tickets saveticket(TicketsPojo ticketsPojo) throws Exception {
        Tickets tickets = null;
        if (ticketsPojo.getId() != null) {
            tickets = ticketsRepository.findByNameAndIdNotIn( ticketsPojo.getName(), ticketsPojo.getId() );
        } else {
            tickets = ticketsRepository.findByName( ticketsPojo.getName() );
        }
        if (tickets == null) {
            tickets = TicketsMapper.mapPojoToEntity( ticketsPojo );
            ticketsRepository.save( tickets );
            JSONObject jsonObject = new JSONObject(ticketsPojo.toString());
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.putPOJO("jsonData", jsonObject);
            String jsonCompleteData = objectNode.toString();

            //Spring Rest Client Call
            String strUrl="";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType( MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(jsonCompleteData, headers);
            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.exchange(strUrl, HttpMethod.POST, entity, String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tickets;
        } else {
            return null;
        }
    }

    public PaymentTypePojo Savespayments(PaymentTypePojo details)throws Exception {
        PosPaymentTypes posPayment = null;
        PaymentTypePojo paymentTypePojo=new PaymentTypePojo();
        SassOrders sassOrders = sassOrdersRepository.findAllBySassOrdersId(details.getSassOrders());
        SassCustomer sassCustomer = sassCustomerRepository.findByEmail(sassOrders.getCompanyEmail());
        if(StringUtils.isEmpty(sassOrders.getLicenceKey())){
            paymentTypePojo.setLicenseKey("activated");
        }
        else {
            paymentTypePojo.setLicenseKey("already activated");
        }
        posPayment = posPaymentTypesRepository.findAllBySassOrders(sassOrders);
        if (posPayment != null) {
            posPayment.setSassOrders(sassOrders);
            posPayment.setPaymentMode(details.getPaymentMode());
            posPayment.setTransactionDate(details.getTransactionDate());
            posPayment.setBalanceAmount(details.getBalanceAmount());
            posPayment.setStatus(details.getStatus());
            posPayment.setTotalAmount(details.getTotalAmount());
            posPayment.setCustomer(sassCustomer);
            posPaymentTypesRepository.save(posPayment);
            if (StringUtils.equalsIgnoreCase(details.getStatus(), "approved")&&sassOrders.getLicenceKey()==null&&!sassOrders.getSassSubscriptionsId().getSubscriptionFor().equalsIgnoreCase("Application")&&!sassOrders.getSassSubscriptionsId().getVersion().equalsIgnoreCase("Cloud" )) {
                List<SassOrders> sassOrders1 = sassOrdersRepository.findBySassOrdersId(details.getSassOrders());
                List<SassOrdersPojo> sassOrdersPojos = SassOrdersMapper.mapEntityToPojo(sassOrders1);
                String licenceKey = sassOrdersService.getLicenceKey(sassOrdersPojos.get(0));
                sassOrders.setLicenceKey(licenceKey);
                SassCompany sassCompany = sassCompanyRepository.findBySassOrdersId(sassOrders);
                sassCompany.setLicenceKey(licenceKey);
                sassCompanyRepository.save(sassCompany);
            }
            sassOrders.setPostingStatus(details.getStatus());
            sassOrdersRepository.save(sassOrders);
        } else {
            posPayment = new PosPaymentTypes();
            posPayment = SaasPaymentTypeMapper.mapPojoToEntity(details);
            posPayment.setCustomer(sassCustomer);
            posPayment.setSassOrders(sassOrders);
            posPaymentTypesRepository.save(posPayment);
            if (StringUtils.equalsIgnoreCase(details.getStatus(), "approved")&&!sassOrders.getSassSubscriptionsId().getSubscriptionFor().equalsIgnoreCase("Application")&&!sassOrders.getSassSubscriptionsId().getVersion().equalsIgnoreCase("Cloud" )) {
                List<SassOrders> sassOrders1 = sassOrdersRepository.findBySassOrdersId(details.getSassOrders());
                List<SassOrdersPojo> sassOrdersPojos = SassOrdersMapper.mapEntityToPojo(sassOrders1);
                String licenceKey = sassOrdersService.getLicenceKey(sassOrdersPojos.get(0));
                sassOrders.setLicenceKey(licenceKey);
                SassCompany sassCompany = sassCompanyRepository.findBySassOrdersId(sassOrders);
                sassCompany.setLicenceKey(licenceKey);
                sassCompanyRepository.save(sassCompany);

            }
            sassOrders.setPostingStatus(details.getStatus());
            sassOrdersRepository.save(sassOrders);
            OrderAppPermission orderAppPermission = new OrderAppPermission();
            orderAppPermission.setSassOrdersId(sassOrders.getSassOrdersId());
            if(sassCustomer!=null)
            orderAppPermission.setCustomerId(sassCustomer.getCustomerId());
            orderAppPermission.setDeveloperCode(sassOrders.getDeveloperId());
                HiproDump hiproDump = hiprodumpRepository.findByAppName(sassOrders.getSassSubscriptionsId().getSubscriptionName());
                if (hiproDump != null) {
                    orderAppPermission.setAppKey(hiproDump.getAppKey());
                    orderAppPermission.setAppName(hiproDump.getAppName());
                    orderAppPermission.setJsonData(hiproDump.getJsonData());
                    orderAppPermission.setSqlString(hiproDump.getSqlString());
                }
                OrderAppSubform orderAppSubform = new OrderAppSubform();
                orderAppSubform.setSassOrdersId(sassOrders.getSassOrdersId());
                if (sassCustomer != null)
                    orderAppSubform.setCustomerId(sassCustomer.getCustomerId());
                orderAppPermission.setDeveloperCode(sassOrders.getDeveloperId());
                if (hiproDump != null) {
                    orderAppSubform.setAppKey(hiproDump.getAppKey());
                    orderAppSubform.setAppName(hiproDump.getAppName());
                    orderAppSubform.setJsonData(hiproDump.getJsonData());
                    orderAppSubform.setSqlString(hiproDump.getSqlString());
                }

                orderAppSubformRepository.save(orderAppSubform);
                orderAppPermissionRepository.save(orderAppPermission);
            EmailServer emailServer = emailServerRepository.findOne(1L);
            if (sassOrders.getSassSubscriptionsId() != null && sassOrders.getSassSubscriptionsId().getSubscriptionFor().equalsIgnoreCase("Application")&& sassOrders.getSassSubscriptionsId().getVersion().equalsIgnoreCase("Cloud")) {
                if (emailServer != null) {
                    String toemail = sassOrders.getCompanyEmail();
                    String subject = sassOrders.getSassSubscriptionsId().getEmailcontent2subject();
                    String body = "URL :" + sassOrders.getSassSubscriptionsId().getAppUrl() + '\n'
                            +"User Name:" + sassOrders.getUsername() + '\n' +
                            "Password:" + sassOrders.getPassword() + '\n' +
                            sassOrders.getSassSubscriptionsId().getEmailcontent2body();
                    String filename = null;
                    MailService.sendMailWithAttachment(emailServer, toemail, subject, body.toString(), "", null, filename);
                }
            }
            else{
                if (emailServer != null) {
                    String toemail = sassOrders.getCompanyEmail();
                    String subject = sassOrders.getSassSubscriptionsId().getEmailcontent2subject();
                    String body = "Licence Key :" + sassOrders.getLicenceKey() + '\n'
                            +"User Name:" + sassOrders.getCustomerName() + '\n' +
                            "Password:" + sassOrders.getPassword() + '\n' + sassOrders.getSassSubscriptionsId().getEmailcontent2body();
                    String filename = null;
                    MailService.sendMailWithAttachment(emailServer, toemail, subject, body.toString(), "", null, filename);
                }
            }


        }
        paymentTypePojo.setSassOrders(sassOrders.getSassOrdersId());
        paymentTypePojo.setPaymentMode(details.getPaymentMode());
        paymentTypePojo.setTransactionDate(details.getTransactionDate());
        paymentTypePojo.setBalanceAmount(details.getBalanceAmount());
        paymentTypePojo.setStatus(details.getStatus());
        paymentTypePojo.setTotalAmount(details.getTotalAmount());
        return paymentTypePojo;

    }

    public List<PaymentTypePojo> paymenTtypeList() {
        List<PosPaymentTypes> payments = new ArrayList<>();
        payments = (List<PosPaymentTypes>) posPaymentTypesRepository.findAll();
        List<PaymentTypePojo> paymentTypePojos = new ArrayList<>();
        for (PosPaymentTypes posPaymentTypes : payments) {
            PaymentTypePojo paymentTypePojo = new PaymentTypePojo();
            paymentTypePojo.setPaymenetId(posPaymentTypes.getPaymenetId());
            if (posPaymentTypes.getCustomer() != null)
                paymentTypePojo.setCustomerId(posPaymentTypes.getCustomer().getCustomerName());
            if (posPaymentTypes.getSassOrders() != null)
                paymentTypePojo.setCompanyName(posPaymentTypes.getSassOrders().getCompanyName());
            paymentTypePojo.setTotalAmount(posPaymentTypes.getTotalAmount());
            paymentTypePojo.setTransactionDate(posPaymentTypes.getTransactionDate());
            paymentTypePojo.setRenewDate(posPaymentTypes.getSassOrders().getRenew().getRenewalDate());
            paymentTypePojos.add(paymentTypePojo);
        }
        return paymentTypePojos;
    }

    public BasePojo getpaginatednotificationList(BasePojo basePojo, String searchText,String restaurantName) {
        List<CustomerNotifications> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "custNotiId"));
        if (basePojo.isLastPage() == true) {
            List<CustomerNotifications> list1 = new ArrayList<>();
            if (!org.springframework.util.StringUtils.isEmpty(searchText)) {
                list1 = getListCustomerNotifications(restaurantName,searchText);
            } else {
                list1 = SassCustomerNotificationsRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        CustomerNotifications notifications = new CustomerNotifications();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "custNotiId"));
        }
        if (!org.springframework.util.StringUtils.isEmpty(searchText)||!StringUtils.isEmpty(restaurantName)) {
            notifications = getFirstObjectCustomerNotifications(restaurantName,sort,searchText);
            list = getPaginatedListCustomerNotifications(restaurantName,pageable,searchText);
        } else {
            notifications = SassCustomerNotificationsRepository.findFirstBy(sort);
            list = SassCustomerNotificationsRepository.findAllBy(pageable);
        }
        if (list.contains(notifications)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<CustomerNotificationsPojo> pojo = CustomerNotificationMapper.mapEntityToPojo(list);
        basePojo = calculatePagination(basePojo, pojo.size());
        basePojo.setList(pojo);
        return basePojo;
    }

    @Transactional
    public List<CustomerNotifications> getListCustomerNotifications(String restaurantName,String searchText) {
        List<CustomerNotifications> list = SassCustomerNotificationsRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("restaurantName"), "%" + searchText.trim() + "%");
                    Predicate predicates1 = cb.like(root.get("fromCompname"), "%" + searchText.trim() + "%");
                    Predicate predicates12 = cb.like(root.get("status"), "%" + searchText.trim() + "%");
                    Predicate or = cb.or(predicates1, predicates, predicates12);
                    predicatesList.add(or);
                }
                if (!StringUtils.isEmpty(restaurantName.trim())) {
                    Predicate predicates = cb.equal(root.get("restaurantName"),  restaurantName.trim());
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        return list;
    }

    @Transactional
    public CustomerNotifications getFirstObjectCustomerNotifications(String restaurantName,Sort sort, String searchText) {
        List<CustomerNotifications> list = SassCustomerNotificationsRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("restaurantName"), "%" + searchText.trim() + "%");
                    Predicate predicates1 = cb.like(root.get("fromCompname"), "%" + searchText.trim() + "%");
                    Predicate predicates12 = cb.like(root.get("status"), "%" + searchText.trim() + "%");
                    Predicate or = cb.or(predicates1, predicates, predicates12);
                    predicatesList.add(or);
                }
                if (!StringUtils.isEmpty(restaurantName.trim())) {
                    Predicate predicates = cb.equal(root.get("restaurantName"),  restaurantName.trim());
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, sort);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return new CustomerNotifications();
        }
    }

    @Transactional
    public List<CustomerNotifications> getPaginatedListCustomerNotifications(String restaurantName,Pageable pageable, String searchText) {
        Page<CustomerNotifications> list = SassCustomerNotificationsRepository.findAll(new Specification<PktPermissions>() {
            @Override
            public Predicate toPredicate(Root<PktPermissions> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("restaurantName"), "%" + searchText.trim() + "%");
                    Predicate predicates1 = cb.like(root.get("fromCompname"), "%" + searchText.trim() + "%");
                    Predicate predicates12 = cb.like(root.get("status"), "%" + searchText.trim() + "%");
                    Predicate or = cb.or(predicates1, predicates, predicates12);
                    predicatesList.add(or);
                }
                if (!StringUtils.isEmpty(restaurantName.trim())) {
                    Predicate predicates = cb.equal(root.get("restaurantName"),  restaurantName.trim());
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, pageable);
        return list.getContent();
    }
    public List<CustomerNotificationsPojo> customernotify(String type) throws Exception {
        List<CustomerNotifications> list = new ArrayList<>();
        List<CustomerNotificationsPojo> customerNotificationsPojoArrayList = new ArrayList<>();
        if (StringUtils.isNotEmpty(type)) {
            list = SassCustomerNotificationsRepository.findAllByTypeDoc(type);
        }
        else {
            list =SassCustomerNotificationsRepository.findAll();
        }
        customerNotificationsPojoArrayList = ObjectMapperUtils.mapAll(list, CustomerNotificationsPojo.class);
        for (CustomerNotificationsPojo customerNotificationsPojo : customerNotificationsPojoArrayList) {
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode object = mapper.readTree(customerNotificationsPojo.getObjectdata());
            final JsonNode order = mapper.readTree(object.get("order").toString());
            String order_id = order.get("order_id").asText();
            customerNotificationsPojo.setOrderId(order_id);
        }
        return customerNotificationsPojoArrayList;
    }

    public List<TransactionsDataPojo> transactiondataList() {
        List<TransactionsData> custnotify = (List<TransactionsData>) SaasTranscationsDataRepository.findAll();
        List<TransactionsDataPojo> cust = ObjectMapperUtils.mapAll(custnotify, TransactionsDataPojo.class);
        return cust;
    }

    public AddOnn SaveAddOn(AddOnnPojo addOnnPojo) {
        AddOnn addOnn = SassAddOnMapper.mapPojoToEntity(addOnnPojo);
        SassSubscriptions sassSubscriptions=sassSubscriptionRepository.findBySubscriptionName((addOnnPojo.getSubscriptionId()));
        addOnn.setSubId(sassSubscriptions);
//        addOnn.setSubId(new SassSubscriptions(Long.parseLong(addOnnPojo.getSubscriptionId())));
        if (addOnn.getAdoneId() != null) {
            addOnn.setAdoneId(addOnnPojo.getAdoneId());
        }
        sassAddOnRepository.save(addOnn);
        return addOnn;
    }
    public static String readDomainNameDashboard() throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = SassControlPanelService.class.getClassLoader().getResourceAsStream("idmwithsass.properties");
            prop.load(in);
            in.close();
        } catch (Exception e) {
        } finally {
            in.close();
        }
        return prop.getProperty("pkt_domain");
    }
    public CartCustomerLink SaveCartCustomer(CartCustomerLinkPojo cartCustomerLinkPojo)throws Exception {
        CartCustomerLink cartCustomerLink = CartCustomerMapper.mapPojoToEntity(cartCustomerLinkPojo);
        Cartregistration reg=cartRegistrationRepository.findByCartName(cartCustomerLinkPojo.getCartId());
        cartCustomerLink.setCartId(reg);
        CartCustomerLink cartCustomerLink1=cartCustomerRepository.findByClientIdAndCartId(cartCustomerLinkPojo.getClientId(),reg);
        if(cartCustomerLink1!=null){
            cartCustomerLink.setCartCustId(cartCustomerLink1.getCartCustId());
        }
        SassCustomer customer=sassCustomerRepository.findDistinctByCustomerName(cartCustomerLinkPojo.getCustomerId());
        cartCustomerLink.setCustomerId(customer);
        cartCustomerRepository.save(cartCustomerLink);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("dm15S1", cartCustomerLinkPojo.getCartId());
        objectNode.put("dm15L1", cartCustomerLink.getCartCustId());
        objectNode.put("dm15S2", cartCustomerLinkPojo.getClientId());
        objectNode.put("dm15S3", cartCustomerLinkPojo.getUrl());
        objectNode.put("dm15S5", cartCustomerLinkPojo.getClientSecret());
        objectNode.put("dm15S4", cartCustomerLinkPojo.getApikey());
        String strUrl=readDomainNameDashboard()+"/DM15/saveDM15";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(objectNode.toString(), headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(strUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartCustomerLink;

    }

    public List<AddOnnPojo> AddOnList() {
        List<AddOnn> addOnns = (List<AddOnn>) sassAddOnRepository.findAll();
        List<AddOnnPojo>list=new ArrayList<>();
        for(AddOnn add:addOnns){
            AddOnnPojo pojo=new AddOnnPojo();
            pojo.setAdoneId(add.getAdoneId());
            pojo.setAddonName(add.getAddonName());
            pojo.setAddonPrice(add.getAddonPrice());
            pojo.setAddonPermission(add.getAddonPermission());
            pojo.setStatus(add.getStatus());
            pojo.setSubscriptionId(add.getSubId().getSubscriptionName().toString());
            list.add(pojo);
        }
//        List<AddOnnPojo> addOnnPojoList = ObjectMapperUtils.mapAll(addOnns, AddOnnPojo.class);
        return list;
    }

    public List<CartCustomerLinkPojo> CartCustomerList(String SearchText) {
        List<CartCustomerLink> link =(List<CartCustomerLink>) cartCustomerRepository.findAll();
        List<CartCustomerLinkPojo> list=new ArrayList<>();
        for(CartCustomerLink link1:link){
            CartCustomerLinkPojo pojo=new CartCustomerLinkPojo();
            pojo.setCartCustId(link1.getCartCustId());
            pojo.setClientId(link1.getClientId());
            pojo.setActivationDate(link1.getActivationDate());
            pojo.setExpiryDate(link1.getExpiryDate());
            pojo.setClientSecret(link1.getClientSecret());
            pojo.setStatus(link1.getStatus());
            pojo.setApikey(link1.getApikey());
            pojo.setUrl(link1.getUrl());
            pojo.setUserName(link1.getUserName());
            pojo.setEmail(link1.getEmail());
            if(link1.getCartId()!=null)
                pojo.setCartId(link1.getCartId().getCartName());
            if(link1.getCustomerId()!=null)
                pojo.setCustomerId(link1.getCustomerId().getCustomerName().toString());
            list.add(pojo);
        }
//        List<CartCustomerLinkPojo> cartCustPojoList = ObjectMapperUtils.mapAll(link, CartCustomerLinkPojo.class);
        return list;
    }

    public Cartregistration saveCartRegistration(CartRegistrationPojo cartRegistrationPojo) {
        Cartregistration cartregistration = CartRegistrationMapper.mapPojoToEntity(cartRegistrationPojo);
        if (cartRegistrationPojo.getCartId() != null) {
            cartRegistrationPojo.setCartId(cartRegistrationPojo.getCartId());
        }
        cartRegistrationRepository.save(cartregistration);

        return cartregistration;


    }

    public IcItem SaveItem(IcItempojo icItempojo) {
        IcItem icItem = IcItemMapper.mapPojoToEntity(icItempojo);
        if (icItem.getItemId() != null) {
            icItem.setItemId(icItempojo.getItemId());
        }
        icItemRepository.save(icItem);
        return icItem;

    }

    public List<CartRegistrationPojo> cartRegistrationPojoList() {
        List<Cartregistration> cartregistrations = (List<Cartregistration>) cartRegistrationRepository.findAll();
        List<CartRegistrationPojo> cartRegistrationPojoList = ObjectMapperUtils.mapAll(cartregistrations, CartRegistrationPojo.class);
        return cartRegistrationPojoList;
    }

    public List<CartCustomerLinkPojo> cartCustomerLinkPojo(){
        List<CartCustomerLink> cartCustomerLinks = (List<CartCustomerLink>) cartCustomerRepository.findAll();
        List<CartCustomerLinkPojo> cartCustomerLinkPojoList = ObjectMapperUtils.mapAll(cartCustomerLinks,CartCustomerLinkPojo.class);
        return cartCustomerLinkPojoList;
    }

    public PractitionerRegistration SavepracReg(PractitionerRegistrPojo Practitioner) {
        PractitionerRegistration register = PractitionerRegMapper.mapPojoToEntity(Practitioner);
        SassSubscriptions sassSubscriptions=sassSubscriptionRepository.findBySubscriptionName((Practitioner.getSubscriptionId()));
        register.setSubscriptionId(sassSubscriptions);
        if (register.getPractitionerId() != null) {
            register.setPractitionerId(Practitioner.getPractitionerId());
        }
        PractitionerRegistrationRepository.save(register);
        return register;

    }

    public List<PractitionerRegistrPojo> practitionerReg(String search) {
        List<PractitionerRegistration> pregister = new ArrayList<>();
        if (StringUtils.isBlank(search)) {
            pregister = (List<PractitionerRegistration>) PractitionerRegistrationRepository.findAll();
        } else {
            pregister = PractitionerRegistrationRepository.findAllByEmail(search);
        }

        List<PractitionerRegistrPojo>pojos=new ArrayList<>();
        for(PractitionerRegistration practitionerRegistration:pregister){
            PractitionerRegistrPojo registrPojo=new PractitionerRegistrPojo();
            registrPojo.setName(practitionerRegistration.getName());
            registrPojo.setEmail(practitionerRegistration.getEmail());
            registrPojo.setCompanyname(practitionerRegistration.getCompanyname());
            registrPojo.setPractitionernumber(practitionerRegistration.getPractitionernumber());
            registrPojo.setPhone(practitionerRegistration.getPhone());
            registrPojo.setStatus(practitionerRegistration.getStatus());
            registrPojo.setPractitionerId(practitionerRegistration.getPractitionerId());
            registrPojo.setPassword(practitionerRegistration.getPassword());
            registrPojo.setSyncUrl(practitionerRegistration.getSyncUrl());
            if(practitionerRegistration.getSubscriptionId()!=null)
            registrPojo.setSubscriptionId(practitionerRegistration.getSubscriptionId().getSubscriptionName().toString());


            pojos.add(registrPojo);
        }

//        List<PractitionerRegistrPojo> register = ObjectMapperUtils.mapAll(pregister, PractitionerRegistrPojo.class);
        return pojos;
    }


    public Practitionerorders Savepracorders(PractitionerordersPojo details) {
        Practitionerorders orders = PractordersMapper.mapPojoToEntity(details);
        SassOrders order=sassOrdersRepository.findByCompanyName(details.getOrderId());
        orders.setOrderId(order);
        PractitionerRegistration reg=PractitionerRegistrationRepository.findByName(details.getPractitionerId());
        orders.setPractitionerId(reg);
//        orders.setPractitionerId(new PractitionerRegistration(Long.parseLong(details.getPractitionerId())));
//        orders.setOrderId(new SassOrders(Long.parseLong(details.getOrderId())));
        if (orders.getPractitionerOrdersId() != null) {
            orders.setPractitionerOrdersId(details.getPractitionerOrdersId());
        }
        PractitionerordersRepository.save(orders);
        return orders;

    }

    public List<PractitionerordersPojo> practorderList() {
        List<Practitionerorders> state = (List<Practitionerorders>) PractitionerordersRepository.findAll();
        List<PractitionerordersPojo>practitionerordersList=new ArrayList<>();
        for(Practitionerorders order:state){
            PractitionerordersPojo pojo=new PractitionerordersPojo();
            pojo.setPractitionerId(String.valueOf(order.getPractitionerId()));
            pojo.setVoucher(order.getVoucher());
            pojo.setActivationKey(order.getActivationKey());
            pojo.setRegistrationKey(order.getRegistrationKey());
            pojo.setCdkey(order.getCdkey());
            pojo.setSvkey(order.getSvkey());
            pojo.setPractitionerOrdersId(order.getPractitionerOrdersId());
            pojo.setDate(order.getDate());
            pojo.setStatus(order.getStatus());
            if(order.getOrderId()!=null)
            pojo.setOrderId(order.getOrderId().getCompanyName().toString());
            if(order.getPractitionerId()!=null)
                pojo.setPractitionerId(order.getPractitionerId().getName().toString());
            practitionerordersList.add(pojo);
        }
//        List<PractitionerordersPojo> PojoList = ObjectMapperUtils.mapAll(state, PractitionerordersPojo.class);
        return practitionerordersList;
    }

    public DestinationType SaveDestination(DestinationTypePojo destdetails) {
        DestinationType dsettype = DestinationTypeMapper.mapPojoToEntity(destdetails);
        if (dsettype.getId() != null) {
            dsettype.setId(destdetails.getId());
        }

        DestinationTypeRepository.save(dsettype);
        return dsettype;

    }

    public List<DestinationTypePojo> destinationList(String sourceType) {
        List<DestinationType> destinationType = null;

        if (!sourceType.equals("All")) {
            destinationType = DestinationTypeRepository.findByDocType(sourceType);
        } else {
            destinationType = (List<DestinationType>) DestinationTypeRepository.findAll();
        }

        List<DestinationTypePojo> destinationTypePojos = ObjectMapperUtils.mapAll(destinationType, DestinationTypePojo.class);

        return destinationTypePojos;
    }

    public List<DestinationTypePojo> destinationtype() {
        List<DestinationType> desttype = (List<DestinationType>) DestinationTypeRepository.findAll();
        List<DestinationTypePojo> PojoList = ObjectMapperUtils.mapAll(desttype, DestinationTypePojo.class);
        return PojoList;
    }

    public DestinationMap SaveDestinationMap(DestinationMapPojo destdetails) {
//        DestinationMap map=null;
        DestinationMap dsettype = DestinationMapMapper.mapPojoToEntity(destdetails);
        if (destdetails.getId() != null) {
            destdetails.setId(destdetails.getId());

        }
        DestinationMapRepository.save(dsettype);
        return dsettype;

    }

    public List<DestinationMapPojo> destinationMapList() {
        List<DestinationMap> map = (List<DestinationMap>) DestinationMapRepository.findAll();
        List<DestinationMapPojo> List = ObjectMapperUtils.mapAll(map, DestinationMapPojo.class);
        return List;
    }

    public DestinationMap getMapObject(String description) {
        DestinationMap destinationMap = DestinationMapRepository.findBysourceName(description);
        return destinationMap;
    }

    public RTRSyncSettings saveRTRSyncsettingDetails(RTRSyncSettingsPojo rtrsync) {
        RTRSyncSettings rtr = RTRSyncSettingsMapper.mapPojoToEntity(rtrsync);
        if (rtrsync.getRtrSyncID() != null) {
            rtrsync.setRtrSyncID(rtrsync.getRtrSyncID());
        }
        RTRSyncSettingsRepository.save(rtr);
        return rtr;
    }

    public List<RTRSyncSettingsPojo> getRTRSyncsettingList(String searchtext) {
        List<RTRSyncSettings> rtr = new ArrayList<>();
        if (StringUtils.isBlank(searchtext)) {
            rtr = (List<RTRSyncSettings>) RTRSyncSettingsRepository.findAll();
        } else {
            rtr = RTRSyncSettingsRepository.findByCompanyKeyWordStartingWith(searchtext);
        }
        List<RTRSyncSettingsPojo> list = ObjectMapperUtils.mapAll(rtr, RTRSyncSettingsPojo.class);
        return list;
    }

    public List<EmailReaderPojo> emailList() {
        List<EmailReader> email = (List<EmailReader>) emailReaderRepository.findAll();
        List<EmailReaderPojo> list = ObjectMapperUtils.mapAll(email, EmailReaderPojo.class);
        return list;
    }

    public EmailReader Saveemail(EmailReaderPojo details) {
//
        EmailReader emaildetails = EmailReaderMapper.mapPojoToEntity(details);
        if (emaildetails.getEmaillistenerId() != null) {
            emaildetails.setEmaillistenerId(details.getEmaillistenerId());
        }

        emailReaderRepository.save(emaildetails);


        return emaildetails;

    }
    public SassSubscriptions savesubscriptionemail(SassSubscriptionsPojo sassSubscriptionsPojo) {
        SassSubscriptions sassSubscriptions =sassSubscriptionRepository.findOne(sassSubscriptionsPojo.getSubscriptionId());
        sassSubscriptions.setEmailServer(sassSubscriptionsPojo.getEmailServer());
            sassSubscriptions.setEmailcontent1body(sassSubscriptionsPojo.getEmailcontent1body());
            sassSubscriptions.setEmailcontent1subject(sassSubscriptionsPojo.getEmailcontent1subject());
            sassSubscriptions.setEmailcontent2subject(sassSubscriptionsPojo.getEmailcontent2subject());
            sassSubscriptions.setEmailcontent2body(sassSubscriptionsPojo.getEmailcontent2body());
            sassSubscriptions.setUrl(sassSubscriptionsPojo.getUrl());
            sassSubscriptions.setVerifyEmail(sassSubscriptionsPojo.getVerifyEmail());
            sassSubscriptions.setRedirecturl(sassSubscriptionsPojo.getRedirecturl());
            sassSubscriptions.setVerifyEmail2(sassSubscriptionsPojo.getVerifyEmail2());
            sassSubscriptionRepository.save(sassSubscriptions);
        return sassSubscriptions;
    }


    public PermissionGroup savepermissionGroup(PermissionGroupDTO permissionGroupDTO) {
        PermissionGroup permissionGroup = SassPermissionGroupMapper.mapPojoToEntity(permissionGroupDTO);
        if (permissionGroupDTO.getPgId() != null) {
            permissionGroup.setPgId(permissionGroupDTO.getPgId());
        }
        sassPermissionGroupRepository.save(permissionGroup);
        return permissionGroup;
    }

    public List<PermissionGroupDTO> getpermissiongroupList1() {
        List<PermissionGroup> list1 = (List<PermissionGroup>) sassPermissionGroupRepository.findAll();
        List<PermissionGroupDTO> list = ObjectMapperUtils.mapAll(list1, PermissionGroupDTO.class);
        return list;
    }

    public List<PermissionMasterDTO> getFirstLevelpermissionMaster1() {
        List<PermissionMaster> list1 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", null);

        List<PermissionMasterDTO> list = ObjectMapperUtils.mapAll(list1, PermissionMasterDTO.class);
        return list;
    }
//    //PKT Table Name Permission master
//    public List<PermissionMasterDTO> getPktTablepermissionMaster() {
//        List<PermissionMaster> list1 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", null);
//
//        List<PermissionMasterDTO> list = ObjectMapperUtils.mapAll(list1, PermissionMasterDTO.class);
//        return list;
//    }


    //get TableNames in PKTFields, Duplicate check Table Name in PKT permission From PKT Fields
    public List<PktFieldsPojo> getPktTablepermissionMaster(String subscriptionName) {
//        List<PktPermission> permissions=pktRepository.findAllByKeySubscriptionAndKeyGroup(subscriptionName,null);
//        List<String> permisionList=new ArrayList<>();
        List<PktFields> list1= null;
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
        list1 = sassPktfieldsRepository.findAllByFieldNameOrFieldNameAndGroupOfOrGroupOfAndStatus(null,"",null,"","Active");
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }
    //getAllTableNameListInPktFields
    public List<PktFieldsPojo> getAllTableNameListInPktFields() {
        List<PktFields> list1
                = sassPktfieldsRepository.findAllByFieldNameOrFieldNameAndGroupOfOrGroupOfAndStatus(null,"",null,"","Active");
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }


    public List<PktFieldsPojo> getPktFieldspermissionMaster(String tableName) {
        List<PktFields> list1 = sassPktfieldsRepository.findAllByTableNameAndStatusAndFieldNameNotNull(tableName,"Active");
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }

    public List<PktPermissionPojo> getPktpermissionMaster(String tableName) {
        List<PktPermission> list1 = pktRepository.findAllByKeyNameAndStatus(tableName,"Active");
        List<PktPermissionPojo> list = ObjectMapperUtils.mapAll(list1, PktPermissionPojo.class);
        return list;
    }


    public List<PermissionMasterDTO> getsecondLevelpermissionMaster1(PermissionMaster permissionmasterId) {
        List<PermissionMaster> list1 = sassPermissionMasterRepository.findAllBySaasStatusAndAndParentPM("Active", permissionmasterId);

        List<PermissionMasterDTO> list = ObjectMapperUtils.mapAll(list1, PermissionMasterDTO.class);
        return list;
    }



//get PKT fields name List
    public List<PktFieldsPojo> permissionTableFieldsNameList(String tableName) {
        List<PktPermission> permissions=pktRepository.findAll();
        List<String> permisionList=new ArrayList<>();
        for(PktPermission pktPermission:permissions){
            permisionList.add(pktPermission.getKeyName());
        }
        List<PktFields> list1 = sassPktfieldsRepository.findAllByTableNameAndStatusAndFieldNameNotIn(tableName,"Active",permisionList);
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }

//    get PKT fields name List#$%
    public List<PktPermissionBean> permissionTableNameLists(String tableName,String application) {
       List<PktPermissions> pktPermissionList = pktPermissionsRepository.findAllByKeyGroupAndKeySubscription(tableName,application);
        List<PktPermissionBean> list = ObjectMapperUtils.mapAll(pktPermissionList, PktPermissionBean.class);
        return list;

    }


//    get PKT fields name List#$%
public List<PktFieldsPojo> getTableCodeList(String keyValue, String keySubscription) {
//    String keyName = null;
    PktPermission permissions = pktRepository.findAllByStatusAndKeyValueAndKeySubscriptionAndKeyGroupIsNull("Active", keyValue, keySubscription);
    if (permissions != null) {
        List<PktFields> pktFields = sassPktfieldsRepository.findAllByTableNameAndStatusAndFieldNameNotNull(permissions.getKeyName(), "Active");
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(pktFields, PktFieldsPojo.class);
        return list;
    } else {
        return null;
    }

}




//get Table Name List In Pkt Permission
    public List<PktPermissionPojo> getTableNameLists(String keyName) {
        List<PktPermission> pktFields = pktRepository.findAllByKeyName(keyName);
        List<PktPermissionPojo> list = ObjectMapperUtils.mapAll(pktFields, PktPermissionPojo.class);
        return list;

    }


    public List<PktFieldsPojo> permissionTableNameList(PktFields Id) {
        List<PktFields> list1 = sassPktfieldsRepository.findAllByStatusAndId("Active", null);
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }

    public List<PktFieldsPojo> permissionFieldsNameList(PktFields Id) {
        List<PktFields> list1 = sassPktfieldsRepository.findAllByStatusAndId("Active", Id);
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }

    public List<PktFieldsPojo> permissionGroupOffList(PktFields Id) {
        List<PktFields> list1 = sassPktfieldsRepository.findAllByStatusAndIdAndGroupOfIsNull("Active", Id);
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }

public List<PktFieldsPojo> getTablesNameList(PktFields subscriptionName) {
        List<PktFields> list1 = sassPktfieldsRepository.findAllByStatusAndIdAndGroupOfIsNull("Active", null);
        List<PktFieldsPojo> list = ObjectMapperUtils.mapAll(list1, PktFieldsPojo.class);
        return list;
    }



    public List<PermissionMasterDTO> getpermissionMasterList() {

        List<PermissionMaster> list1 = (List<PermissionMaster>) sassPermissionMasterRepository.findAll();
        List<PermissionMasterDTO> permissionMasters = new ArrayList<>();
//
        for (PermissionMaster permission : list1) {
            PermissionMasterDTO permissionMasterDTO = new PermissionMasterDTO();
            permissionMasterDTO.setDescription(permission.getDescription());
            permissionMasterDTO.setSaasStatus(permission.getSaasStatus());
            permissionMasterDTO.setPmId(permission.getPmId());
            permissionMasterDTO.setPermissionMasterkey(permission.getPermissionMasterkey());
            permissionMasterDTO.setLevelType(permission.getLevelType());
            permissionMasterDTO.setPgId(permission.getPermissionGroupId().getPgId());
            if(StringUtils.equalsIgnoreCase(permission.getLevelType(),"Level2")){
                permissionMasterDTO.setPermId(permission.getParentPM().getPmId());
            }else if(StringUtils.equalsIgnoreCase(permission.getLevelType(),"Level3")){
                permissionMasterDTO.setPerm2(permission.getParentPM().getPmId());
                permissionMasterDTO.setPermId(permission.getParentPM().getParentPM().getPmId());
            }else if(StringUtils.equalsIgnoreCase(permission.getLevelType(),"Level4")){
                permissionMasterDTO.setPerm3(permission.getParentPM().getPmId());
                permissionMasterDTO.setPerm2(permission.getParentPM().getParentPM().getPmId());
                permissionMasterDTO.setPermId(permission.getParentPM().getParentPM().getParentPM().getPmId());
            }
            permissionMasters.add(permissionMasterDTO);
//            List<PermissionMasterDTO> list = ObjectMapperUtils.mapAll(permissionMasters, PermissionMasterDTO.class);
//        return list;
        }
        return permissionMasters;
    }

    public PermissionMaster savepermissionmaster(PermissionMasterDTO permissionMasterDTO) {
        PermissionMaster permissionMaster = null;
        if(permissionMasterDTO.getId()!=null){
            permissionMaster=sassPermissionMasterRepository.findOne(permissionMasterDTO.getId());
            permissionMaster.setDescription(permissionMasterDTO.getDescription());
            permissionMaster.setSaasStatus(permissionMasterDTO.getSaasStatus());
            permissionMaster.setPermissionMasterkey(permissionMasterDTO.getPermissionMasterkey());
        }
        else{
            permissionMaster = SassPermissionMasterMapper.mapPojoToEntity(permissionMasterDTO);
        }
        if (permissionMasterDTO.getPmId() == null) {
            permissionMaster.setPermissionGroupId(new PermissionGroup(permissionMasterDTO.getPgId()));
            permissionMaster.setLevelType("Level1");
        } else if (permissionMasterDTO.getPmId() != null) {
            permissionMaster.setParentPM(new PermissionMaster(permissionMasterDTO.getPmId()));
            permissionMaster.setPermissionGroupId(new PermissionGroup(permissionMasterDTO.getPgId()));
            permissionMaster.setLevelType("Level2");
        }
        if (permissionMasterDTO.getPermId() != null) {
            permissionMaster.setParentPM(new PermissionMaster(permissionMasterDTO.getPermId()));
            permissionMaster.setLevelType("Level3");
        }
        if (permissionMasterDTO.getPerm3() != null) {
            permissionMaster.setParentPM(new PermissionMaster(permissionMasterDTO.getPerm3()));
            permissionMaster.setLevelType("Level4");
        }
        if (permissionMasterDTO.getPerm4() != null) {
            permissionMaster.setParentPM(new PermissionMaster(permissionMasterDTO.getPerm4()));
        }
        sassPermissionMasterRepository.save(permissionMaster);
        return permissionMaster;

    }

    public PermissionMaster saveeditpermissionmaster1(PermissionMasterDTO permissionMasterDTO) {
        PermissionMaster permissionMaster = new PermissionMaster();

        permissionMaster = sassPermissionMasterRepository.findByPmId(permissionMasterDTO.getPmId());
        PermissionMaster master = new PermissionMaster();
        if (permissionMaster.getPmId() != null) {
            permissionMaster.setDescription(permissionMasterDTO.getDescription());
            permissionMaster.setPermissionMasterkey(permissionMasterDTO.getPermissionMasterkey());
            permissionMaster.setParentPM(permissionMaster.getParentPM());
            permissionMaster.setSaasStatus(permissionMaster.getSaasStatus());
            permissionMaster.setPermissionGroupId(permissionMaster.getPermissionGroupId());
            sassPermissionMasterRepository.save(permissionMaster);

        }
        return permissionMaster;
    }

    public PermissionMaster deletepermissionmaster(PermissionMasterDTO permissionMasterDTO) {
        PermissionMaster permissionMaster = new PermissionMaster();
        permissionMaster = sassPermissionMasterRepository.findByPmId(permissionMasterDTO.getPmId());
        permissionMaster.setSaasStatus("InActive");
        sassPermissionMasterRepository.save(permissionMaster);
//    sassPermissionMasterRepository.delete(permissionMaster);
        return permissionMaster;
    }

    public PermissionGroup deletegroup(PermissionGroupDTO permissionGroupDTO) {
        PermissionGroup permissiongroup = new PermissionGroup();
        permissiongroup = sassPermissionGroupRepository.findByPgId(permissionGroupDTO.getPgId());
        permissiongroup.setSaasStatus("InActive");
        sassPermissionGroupRepository.save(permissiongroup);
//        sassPermissionGroupRepository.delete(permissiongroup);
        return permissiongroup;
    }

//Save PKT Permission Table
public PktPermission savePktPermsissionTable(PktPermissionPojo pktPermissionPojo) {
    PktPermission pktPermission = PktMapper.mapPktPermissionPojoToEnity(pktPermissionPojo);
    PktPermission permission = null;
    if (pktPermission.getPktPermissionId() != null) {
        pktPermission.setPktPermissionId(pktPermission.getPktPermissionId());
        permission = pktRepository.findByKeyNameAndKeySubscriptionAndPktPermissionIdNotIn(pktPermissionPojo.getKeyName(),pktPermissionPojo.getKeySubscription(),pktPermissionPojo.getKeyValue(),pktPermissionPojo.getPktPermissionId());
    }else {
        permission = pktRepository.findByKeyNameAndKeySubscription(pktPermissionPojo.getKeyName(),pktPermissionPojo.getKeySubscription(),pktPermissionPojo.getKeyValue());
    }
    if(permission == null){
        pktRepository.save(pktPermission);
    }else {
        return null;
    }
    return pktPermission;

}

    //Save PKT Permission TableColumnMapping
    public PktPermission savePktPermsissionTableColumnMapping(PktPermissionPojo pktPermissionPojo) {
        List<PktPermission> permission = null;
        PktPermission pktPermission = PktMapper.mapPktPermissionPojoToEnity(pktPermissionPojo);
        String Subscription = null;
        String Operator = null;
        String TableName = null;
        String ColumnName = null;
        String permissiona= null;
        if(pktPermissionPojo.getPktPermissionId()==null){
            permission = pktRepository.findByKeySubscriptionAndTableNameAndColumnNameAndOperatorAndKeyNameAndKeyAction(pktPermissionPojo.getKeySubscription(),pktPermissionPojo.getTableName(),pktPermissionPojo.getColumnName(),pktPermissionPojo.getOperator(),pktPermissionPojo.getKeyName(),pktPermissionPojo.getKeyAction());
            if(!permission.isEmpty()){
                return  null;
            }

        }
// if (pktPermissionPojo.getPktPermissionId() == null) {
//            permission = pktRepository.findAllByKeyName(pktPermissionPojo.getKeyName());
//            for (PktPermission pktPermission1 : permission) {
//                permissiona = pktPermission1.getKeyName();
//            }
//            List<PktPermission> pktPermissionList = pktRepository.findAllByKeySubscription(pktPermissionPojo.getKeySubscription());
//            for (PktPermission pktPermission1 : pktPermissionList) {
//                Subscription = pktPermission1.getKeySubscription();
//            }
//            List<PktPermission> pktPermission1 = pktRepository.findAllByTableName(pktPermissionPojo.getTableName());
//            for (PktPermission pktPermission2 : pktPermission1) {
//                TableName = pktPermission2.getTableName();
//            }
//
//            List<PktPermission> pktPermission2 = pktRepository.findAllByOperator(pktPermissionPojo.getOperator());
//            for (PktPermission pktPermission3 : pktPermission2) {
//                Operator = pktPermission3.getOperator();
//            }
//            List<PktPermission> pktPermission3 = null;
//            if (pktPermissionPojo.getColumnName() != null) {
//                pktPermission3 = pktRepository.findAllByColumnName(pktPermissionPojo.getColumnName());
//            }
//
//            for (PktPermission pktPermission4 : pktPermission3) {
//                ColumnName = pktPermission4.getColumnName();
//            }
//
//            if (ColumnName != null) {
//                if (permissiona != null) {
//                    if (Operator != null) {
//                        if (Subscription.equalsIgnoreCase(pktPermissionPojo.getKeySubscription())
//                                && TableName.equalsIgnoreCase(pktPermissionPojo.getTableName())
//                                && Operator.equalsIgnoreCase(pktPermissionPojo.getOperator())
//                                && ColumnName.equalsIgnoreCase(pktPermissionPojo.getColumnName())
//                                && permissiona.equalsIgnoreCase(pktPermissionPojo.getKeyName())) {
//                            return null;
//                        }
//                    }
//
//                }
//            }
//        }
        if (pktPermission.getPktPermissionId() != null) {
            permission = pktRepository.findByKeySubscriptionAndTableNameAndColumnNameAndOperatorAndKeyNameAndKeyActionAndPktPermissionIdNotIn(pktPermissionPojo.getKeySubscription(),pktPermissionPojo.getTableName(),pktPermissionPojo.getColumnName(),pktPermissionPojo.getOperator(),pktPermissionPojo.getKeyName(),pktPermissionPojo.getKeyAction(),pktPermission.getPktPermissionId());
            if(!permission.isEmpty()){
                return  null;
            }
            pktPermission.setPktPermissionId(pktPermission.getPktPermissionId());

        }
        List<PktPermission>  pktPermission1 =pktRepository.findBykeyValue(pktPermission.getTableName());
        for (PktPermission pktPermission2 :pktPermission1) {
            pktPermission.setKeyGroup(pktPermission2.getKeyName());

        }
        pktRepository.save(pktPermission);
        return pktPermission;

    }
    public Permission savePermsission(PermissionPojo permissionPojo) {
        Permission permission = PermissionMapper.mapPermissionPojoToEntity(permissionPojo);
        if (permissionPojo.getPermissionId() != null) {
            permission.setPermissionId(permissionPojo.getPermissionId());

        }
        permissionRepository.save(permission);
        return permission;

    }
    public List<PermissionPojo> getAllPermissionList(){
        List<Permission> permissionList =permissionRepository.findAll();
       List<PermissionPojo> permissionPojoList = PermissionMapper.mapPermissionEntityToPojo(permissionList);
        return  permissionPojoList;

    }



    public ColumnType saveColumnType(ColumnTypePojo columnTypePojo) {
        ColumnType columnType = ColumnTypeMapper.mapColumnTypePojoToEntity(columnTypePojo);
//        if (columnTypePojo.getId() != null) {
//            columnType.setId(columnTypePojo.getId());
//
//        }
        columnTypeRepository.save(columnType);
        return columnType;

    }


    public List<ColumnTypePojo> getAllColumnTypeList(){
        List<ColumnType> columnTypeList =columnTypeRepository.findAll();
        List<ColumnTypePojo> permissionPojoList = ColumnTypeMapper.mapColumnTypeEntityToPojo(columnTypeList);
        return  permissionPojoList;

    }

    public List<PktPermissionPojo> getPktPermisssionList(String subscription) {
//        List<PktPermission> list1 =(List<PktPermission>) pktRepository.findAllByStatus("Active");
        List<PktPermission> list1 = (List<PktPermission>) pktRepository.findAllByKeyGroupAndKeySubscription(null,subscription);
        List<PktPermissionPojo> list = ObjectMapperUtils.mapAll(list1, PktPermissionPojo.class);
        return list;
    }




    public List<PktPermissionPojo> getColumnPermisssionList() {
        List<PktPermission> list1 =  pktRepository.findAllByTableNameNotNullAndColumnNameNotNullAndOperatorNotNull();
        List<PktPermissionPojo> list = ObjectMapperUtils.mapAll(list1, PktPermissionPojo.class);
        return list;
    }

    public BasePojo getPaginatedfieldList(String status, BasePojo basePojo, String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        List<PktPermission> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "pktPermissionId"));

        if (basePojo.isLastPage() == true) {
            List<PktPermission> list1 = new ArrayList<>();

            if (!StringUtils.isEmpty(searchText.trim()) ||
                    (StringUtils.isNotBlank(tablesearchText) ||
                            (StringUtils.isNotBlank(keysearchText) ||
                                    (StringUtils.isNotBlank(columnsearchText) ||
                                            (StringUtils.isNotBlank(operatorsearchText)))))) {
                list1 = getList(searchText,tablesearchText,keysearchText,columnsearchText,operatorsearchText);
            } else {
                list1 = pktRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        PktPermission fields = new PktPermission();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "pktPermissionId"));
        }
        if (!StringUtils.isEmpty(searchText.trim()) || (StringUtils.isNotBlank(tablesearchText) ||
                        (StringUtils.isNotBlank(keysearchText) ||
                                (StringUtils.isNotBlank(columnsearchText) ||
                                        (StringUtils.isNotBlank(operatorsearchText)))))) {

            fields = getFirstObject(sort,searchText,tablesearchText,keysearchText,columnsearchText,operatorsearchText);
            list = getPaginatedList(pageable,searchText,tablesearchText,keysearchText,columnsearchText,operatorsearchText);
        } else {
            fields = pktRepository.findFirstByStatus(status, sort);
            list = pktRepository.findAllByStatus(status, pageable);
        }
        if(fields!=null) {
            if (list.contains(fields)) {
                basePojo.setStatus(true);
            } else {
                basePojo.setStatus(false);
            }
            List<PktPermissionPojo> pojo = PktMapper.mapEntityToPojo(list);
            basePojo = calculatePagination(basePojo, pojo.size());
            basePojo.setList(pojo);
            return basePojo;
        }
        else{
            return null;
        }
    }


    public List<PktPermission> getPaginatedList(Pageable pageable,String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        Page<PktPermission> list = pktRepository.findAll(new Specification<PktPermission>() {
            @Override
            public Predicate toPredicate(Root<PktPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"),"%"+searchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(tablesearchText.trim())) {
                    Predicate predicates = cb.like(root.get("tableName"),"%"+tablesearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(keysearchText.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"),"%"+keysearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(columnsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("columnName"),"%"+columnsearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(operatorsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("operator"),"%"+operatorsearchText.trim()+"%");
                    predicatesList.add(predicates);
                }

                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        },pageable);
        if(list.getContent().size()>0) {
            return list.getContent();
        }
        else{
            return null;
        }
    }


    public PktPermission getFirstObject(Sort sort,String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        List<PktPermission> list = pktRepository.findAll(new Specification<PktPermission>() {
            @Override
            public Predicate toPredicate(Root<PktPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"),"%"+searchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(tablesearchText.trim())) {
                    Predicate predicates = cb.like(root.get("tableName"),"%"+tablesearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(keysearchText.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"),"%"+keysearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(columnsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("columnName"),"%"+columnsearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(operatorsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("operator"),"%"+operatorsearchText.trim()+"%");
                    predicatesList.add(predicates);
                }

                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        },sort);
        if(list.size()>0) {
            return list.get(0);
        }
        else{
            return null;
        }
    }


    public List<PktPermission> getList(String searchText, String tablesearchText, String keysearchText, String columnsearchText, String operatorsearchText) {
        List<PktPermission> list = pktRepository.findAll(new Specification<PktPermission>() {
            @Override
            public Predicate toPredicate(Root<PktPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"),"%"+searchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(tablesearchText.trim())) {
                    Predicate predicates = cb.like(root.get("tableName"),"%"+tablesearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(keysearchText.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"),"%"+keysearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(columnsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("columnName"),"%"+columnsearchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(operatorsearchText.trim())) {
                    Predicate predicates = cb.like(root.get("operator"),"%"+operatorsearchText.trim()+"%");
                    predicatesList.add(predicates);
                }

                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        if(list.size()>0) {
            return list;
        }
        else{
            return null;
        }
    }
    // get Table Name from Pkt permission table
    public List<PktPermissionPojo> getTableNameList(String subscriptionName) {
//        List<PktPermission> permissions=pktRepository.findAll();
//        List<String> permisionList=new ArrayList<>();
//        for(PktPermission pktPermission:permissions){
//            permisionList.add(pktPermission.getKeyName());
//        }
        List<PktPermission> list1 =pktRepository.findAllByKeyGroupOrKeyGroupAndKeySubscriptionAndStatus(null,"",subscriptionName,"Active");
        List<PktPermissionPojo> list = ObjectMapperUtils.mapAll(list1, PktPermissionPojo.class);
        return list;
    }
    //GetAll Permission TO PKT
    public List<PktPermissions> getPktPermissionList(String jsonString) throws JSONException {

//        JSONObject jsonObject = new JSONObject(jsonString);
//        String value = jsonObject.get("subscription").toString();
        List<PktPermissions> pktPermissionList  = pktPermissionsRepository.findAllByKeySubscription(jsonString);
        List<PktPermissionBean> list = ObjectMapperUtils.mapAll(pktPermissionList,PktPermissionBean.class);
        return  pktPermissionList;
    }


    public List<defaultPermissions> getDefPktPermissionList() throws JSONException {
        List<defaultPermissions> defList  = defaultPermissionsRepository.findAll();
        return  defList;
    };
    public PermissionMaster getPermissionMasterById(Long permissionmasterId) {
        PermissionMaster permissionMaster = sassPermissionMasterRepository.findByPmId(permissionmasterId);
        return permissionMaster;
    }
    public BasePojo getPaginatedOperatorList(BasePojo basePojo, String searchText) {
        List<Operator> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<Operator> list1 = new ArrayList<>();
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
        Operator operator = new Operator();
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
        List<Operatorpojo> operatorpojoList = OperatorMapper.mapOperatorEntityToPojo(list);
        basePojo = calculatePagination(basePojo, operatorpojoList.size());
        basePojo.setList(operatorpojoList);
        return basePojo;
    }
    public BasePojo getPaginatedActionList(BasePojo basePojo, String searchText) {
        List<Action> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<Action> list1 = new ArrayList<>();
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
        Action action = new Action();
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
        List<ActionPojo> actionPojoList = ActionMapper.mapOperatorEntityToPojo(list);
        basePojo = calculatePagination(basePojo, actionPojoList.size());
        basePojo.setList(actionPojoList);
        return basePojo;
    }


    public BasePojo getPaginatedTableList(BasePojo basePojo, String searchText,String searchText1,String searchText2) {
        List<PktPermission> list = new ArrayList<>();
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
            List<PktPermission> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText.trim()) ||
                    (StringUtils.isNotBlank(searchText1) ||
                            (StringUtils.isNotBlank(searchText2))))

                                            {
                list1 = getList1(searchText,searchText1,searchText2);
//                list1 = pktRepository.findAllByKeyName(searchText);
            } else {
                list1 = pktRepository.findAllByKeyGroup(null);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        PktPermission fields = new PktPermission();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "pktPermissionId"));
        }
        if (!StringUtils.isEmpty(searchText.trim()) ||
                (StringUtils.isNotBlank(searchText1) ||
                        (StringUtils.isNotBlank(searchText2))))

        {
            fields = getFirstObject1(sort,searchText,searchText1,searchText2);
            list = getPaginatedList1(pageable,searchText,searchText1,searchText2);
//            fields = pktRepository.findFirstByKeyNameOrKeySubscriptionOrKeyValue(searchText,sort,searchText1,searchText2);
//            list = pktRepository.findAllByKeyNameOrKeySubscriptionOrKeyValue(searchText, pageable,searchText1,searchText2);
        } else {
            fields = pktRepository.findFirstBy(sort);
            list = pktRepository.findByKeyGroup(null,pageable);
        }
        if(fields!=null) {
            if (list.contains(fields)) {
                basePojo.setStatus(true);
            } else {
                basePojo.setStatus(false);
            }
            List<PktPermissionPojo> pojo = PktMapper.mapEntityToPojo(list);
            basePojo = calculatePagination(basePojo, pojo.size());
            basePojo.setList(pojo);
            return basePojo;
        }
        else{
            return null;
        }
    }




    public List<PktPermission> getList1(String searchText, String searchText1, String searchText2) {
        List<PktPermission> list = pktRepository.findAll(new Specification<PktPermission>() {
            @Override
            public Predicate toPredicate(Root<PktPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"),"%"+searchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText1.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"),"%"+searchText1.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText2.trim())) {
                    Predicate predicates = cb.like(root.get("keyValue"),"%"+searchText2.trim()+"%");
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        if(list.size()>0) {
            return list;
        }
        else{
            return null;
        }
    }




    public PktPermission getFirstObject1(Sort sort,String searchText, String searchText1, String searchText2) {
        List<PktPermission> list = pktRepository.findAll(new Specification<PktPermission>() {
            @Override
            public Predicate toPredicate(Root<PktPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"),"%"+searchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText1.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"),"%"+searchText1.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText2.trim())) {
                    Predicate predicates = cb.like(root.get("keyValue"),"%"+searchText2.trim()+"%");
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        },sort);
        if(list.size()>0) {
            return list.get(0);
        }
        else{
            return null;
        }
    }


    public List<PktPermission> getPaginatedList1(Pageable pageable,String searchText, String searchText1, String searchText2) {
        Page<PktPermission> list = pktRepository.findAll(new Specification<PktPermission>() {
            @Override
            public Predicate toPredicate(Root<PktPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("keySubscription"),"%"+searchText.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText1.trim())) {
                    Predicate predicates = cb.like(root.get("keyName"),"%"+searchText1.trim()+"%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(searchText2.trim())) {
                    Predicate predicates = cb.like(root.get("keyValue"),"%"+searchText2.trim()+"%");
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        },pageable);
        if(list.getContent().size()>0) {
            return list.getContent();
        }
        else{
            return null;
        }
    }




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

    public EmailServer saveEmailServer(EmailServerPojo emailServer) {
        EmailServer email = EmailServerMapper.mapEmailSeverPojoToEntity(emailServer);

        emailServerRepository.save(email);
        return email;

    }
    public EmailServerPojo editMaill(String name) {
        EmailServer mail = emailServerRepository.findByUserName(name);
        List<EmailServer> mails = new ArrayList<>();
        mails.add(mail);
        EmailServerPojo mailDTO = EmailServerMapper.mapMailEntityToPojo(mails).get(0);
        return mailDTO;
    }
    public void deleteMail(String userName) {
        emailServerRepository.delete(emailServerRepository.findByUserName(userName));
    }

    public List<EmailServerPojo> getemailserverlist() {
        List<EmailServer> list1 = (List<EmailServer>) emailServerRepository.findAll();
        List<EmailServerPojo> list = ObjectMapperUtils.mapAll(list1, EmailServerPojo.class);
        return list;
    }

    public PktBuilder saveModules(String object)throws Exception{
        JSONObject jsonObject=new JSONObject(object);
        String applicationName=jsonObject.getString("applicationName");
        if(StringUtils.equalsIgnoreCase(jsonObject.getString("tableRequired"),"true")){
            PktPermissions pktPermissions = pktPermissionsRepository.findByKeyGroupNullAndKeyNameAndKeySubscription(jsonObject.get("name").toString(),applicationName);
                if(pktPermissions!=null){
                    return null;
                }

        }

        PktBuilder pktbuilder=pktbuilderRepository.findByApplicationName(applicationName);
        if(pktbuilder==null){
            Gson gson = new Gson();
            pktbuilder = new PktBuilder();
            pktbuilder.setApplicationName(applicationName);
            List<MenuPojo> menuPojos= new ArrayList<>();
            if(StringUtils.equalsIgnoreCase(jsonObject.get("moduleName").toString(),"createNew")){
                MenuPojo menuPojo=new MenuPojo();
                menuPojo.setMenuName(jsonObject.get("name").toString());
                menuPojo.setSequenceno(String.valueOf(1));
                menuPojo.setKeySubscription(jsonObject.get("applicationName").toString());
                menuPojo.setParentValue(jsonObject.get("name").toString());
                menuPojo.setTableRequired(jsonObject.get("tableRequired").toString());
                menuPojo.setPageRequired(jsonObject.get("pageRequired").toString());
                menuPojos.add(menuPojo);
                pktbuilder.setBuilderValue(gson.toJson(menuPojos));
                pktbuilder.setStatus("Active");
            }


        }
        else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<MenuPojo>>() {
            }.getType();
            List<MenuPojo> menuPojos = gson.fromJson(pktbuilder.getBuilderValue(), type);
            if (StringUtils.equalsIgnoreCase(jsonObject.get("moduleName").toString(), "createNew")) {
                MenuPojo menuPojo = new MenuPojo();
                menuPojo.setMenuName(jsonObject.get("name").toString());
                menuPojo.setSequenceno(String.valueOf(menuPojos.size() + 1));
                menuPojo.setKeySubscription(jsonObject.get("applicationName").toString());
                menuPojo.setParentValue(jsonObject.get("name").toString());
                menuPojo.setTableRequired(jsonObject.get("tableRequired").toString());
                menuPojo.setPageRequired(jsonObject.get("pageRequired").toString());
                menuPojos.add(menuPojo);
                pktbuilder.setBuilderValue(gson.toJson(menuPojos));
                pktbuilder.setPageRequired(Boolean.valueOf(jsonObject.get("pageRequired").toString()));

            } else if (StringUtils.equalsIgnoreCase(jsonObject.get("subModule").toString(), "createNew")) {
                for (MenuPojo menuPojo : menuPojos) {
                    if (StringUtils.equalsIgnoreCase(menuPojo.getMenuName(), jsonObject.get("moduleName").toString())) {
                        Type subType = new TypeToken<List<MenuParentPojo>>() {
                        }.getType();
                        List<MenuParentPojo> menuParentPojos = gson.fromJson(menuPojo.getParentId(), subType);
                        if (menuParentPojos == null) {
                            menuParentPojos = new ArrayList<>();
                        }
                        MenuParentPojo menuParentPojo = new MenuParentPojo();
                        menuParentPojo.setParentGroup(jsonObject.get("moduleName").toString());
                        menuParentPojo.setParentValue(jsonObject.get("name").toString());
                        menuParentPojo.setSequencenumber(String.valueOf(menuParentPojos.size() + 1));
                        menuParentPojo.setTableRequired(jsonObject.get("tableRequired").toString());
                        menuParentPojo.setPageRequired(jsonObject.get("pageRequired").toString());
                        menuParentPojos.add(menuParentPojo);
                        menuPojo.setParentId(gson.toJson(menuParentPojos));
                        pktbuilder.setBuilderValue(gson.toJson(menuPojos));
                        pktbuilder.setPageRequired(Boolean.valueOf(jsonObject.get("pageRequired").toString()));
                    }
                }
            }
        }
        pktbuilderRepository.save(pktbuilder);
        if(StringUtils.equalsIgnoreCase(jsonObject.getString("tableRequired"),"true")){
            PktPermissions permission=new PktPermissions();
            permission.setKeyName(jsonObject.get("name").toString());
            permission.setKeySubscription(jsonObject.get("applicationName").toString());
            permission.setKeyValue(jsonObject.get("name").toString());
            permission.setTableName(jsonObject.get("name").toString());
            permission.setOperator(jsonObject.get("name").toString());
            permission.setMenuKey(jsonObject.get("moduleName").toString());
            if(StringUtils.equalsIgnoreCase(jsonObject.get("moduleName").toString(),"createNew")){
                permission.setSubMenuKey(jsonObject.get("name").toString());
            }
            else{
                permission.setSubMenuKey(jsonObject.get("subModule").toString());
            }
            permission.setStatus("Active");
            pktPermissionsRepository.save(permission);
        }
        return pktbuilder;

    }

    public PktBuilder getpktbuilderbyApplication(String application){
        PktBuilder pktbuilder=pktbuilderRepository.findByApplicationName(application);
        Gson gson=new Gson();
        Type listType = new TypeToken<Map<String,String>>() {
        }.getType();
        Map<String,String> result=new HashMap<>();
        if(pktbuilder!=null) {
            if(!StringUtils.isEmpty(pktbuilder.getHeaderValue())) {
                result = gson.fromJson(pktbuilder.getHeaderValue(), listType);
                if (!StringUtils.isEmpty(result.get("logo")))
                    if (!result.get("logo").equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + pktbuilder.getApplicationName() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        result.put("logo", imageLocation);
                        pktbuilder.setHeaderValue(gson.toJson(result));
                    }
            }
        }
        return pktbuilder;
    }


    public List<MenuPojo> getpktbuilderValuesbyApplication(String application,String menuKey){
        List<PktPermissions> pktPermissions = pktPermissionsRepository.findAllByKeySubscriptionAndSubMenuKey(application,menuKey);
        List<MenuPojo> menuPojoList = new ArrayList<>();
        for(PktPermissions permissions:pktPermissions){
            MenuPojo menuPojo = new MenuPojo();
            menuPojo.setMenuName(permissions.getKeyName());
            menuPojo.setPageRequired("true");
            menuPojo.setTableRequired("true");
            menuPojoList.add(menuPojo);
        }

        if(pktPermissions.size()==0) {
            PktBuilder pktBuilder = pktbuilderRepository.findByApplicationName(application);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<MenuPojo>>() {
            }.getType();
            List<MenuPojo> menuPojos = gson.fromJson(pktBuilder.getBuilderValue(), listType);
            List<MenuParentPojo> menuParentPojo = new ArrayList<>();
            for (MenuPojo menu : menuPojos) {
                if (StringUtils.equalsIgnoreCase(menu.getMenuName(), menuKey)) {

                    Type type = new TypeToken<List<MenuParentPojo>>() {
                    }.getType();
                    menuParentPojo = gson.fromJson(menu.getParentId(), type);
                }
            }
            for (MenuParentPojo menuParentPojo1 : menuParentPojo) {
                MenuPojo menuPojo = new MenuPojo();
                menuPojo.setMenuName(menuParentPojo1.getParentValue());
                menuPojo.setPageRequired(menuParentPojo1.getPageRequired());
                menuPojo.setTableRequired(menuParentPojo1.getTableRequired());
                menuPojoList.add(menuPojo);
            }
        }
        return menuPojoList;
    }


    public void deletePermissions (String id){
        PktPermissions pktPermissions = new PktPermissions();
         pktPermissions = pktPermissionsRepository.findAllByPktPermissionId(Long.parseLong(id));
        pktPermissionsRepository.delete(pktPermissions);

    }

    public String saveWebPermission(String webpermissions)throws  Exception{
        Webpermission webpermission = new Webpermission();
        JSONObject jsonObject = new JSONObject(webpermissions);
        webpermission.setEle_height(jsonObject.get("ele_height").toString());
        webpermission.setEle_left(jsonObject.get("ele_left").toString());
        webpermission.setEle_width(jsonObject.get("ele_width").toString());
        webpermission.setPosition(jsonObject.get("ele_position").toString());
        webpermission.setClassName(jsonObject.get("classname").toString());
        webpermission.setTagName(jsonObject.get("tagname").toString());
        webpermission.setEle_top(jsonObject.get("top").toString());
        webpermission.setBackground_color(jsonObject.get("background_color").toString());
        webpermission.setFont_size(jsonObject.get("font_size").toString());
        webpermission.setColor(jsonObject.get("color").toString());
        webpermission.setText_align(jsonObject.get("text_align").toString());
        webpermission.setApplicationName(jsonObject.get("applicationName").toString());
        webpermission.setPageName(jsonObject.get("pageName").toString());
        webpermission.setTableName(jsonObject.get("tableName").toString());

        webPermissionRepository.save(webpermission);
        return  "success";


    }

    public List<WebPermissionPojo> getwebpermissionList(String list) throws Exception {
        JSONObject jsonObject = new JSONObject(list);
        List<Webpermission> webList=webPermissionRepository.findAllByApplicationNameAndTableNameAndPageName(jsonObject.get("applicationName").toString(),jsonObject.get("tableName").toString(),jsonObject.get("pageName").toString());
        List<WebPermissionPojo> webPermissionPojos =ObjectMapperUtils.mapAll(webList,WebPermissionPojo.class);
        return webPermissionPojos;
    }
}

