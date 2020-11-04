package com.hyva.idm.sass.sassendpoints;

import com.hyva.idm.constants.EndpointConstants;
import com.hyva.idm.icitem.IcItem;
import com.hyva.idm.icitem.IcItempojo;
import com.hyva.idm.pkt.pktBeans.PktPermissionBean;
import com.hyva.idm.pkt.pktRelations.PktBuilder;
import com.hyva.idm.pkt.pktRelations.PktPermissions;
import com.hyva.idm.pojo.IDMResponse;
import com.hyva.idm.sass.sassentities.*;
import com.hyva.idm.sass.sasspojo.*;
import com.hyva.idm.sass.sasspojo.EmailServerPojo;
import com.hyva.idm.sass.sassrespositories.EmailServerRepository;
import com.hyva.idm.sass.sassservice.SassControlPanelService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(EndpointConstants.SERVICES_ENDPOINT)

public class ControlPanelEndpoint extends HttpServlet {
    @Autowired
    SassControlPanelService sassControlPanelService;

    @RequestMapping(value = "/getPaginatedCurrencyList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPaginatedCurrencyList() {
        List<SassCurrencyPojo> sassCurrencyPojos = sassControlPanelService.CurrencyList();
        return new IDMResponse(HttpStatus.OK.value(), "success", sassCurrencyPojos);
    }

    @RequestMapping(value = "/saveNewCurrency",
            method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public SassCurrency saveNewCurrency(@RequestBody SassCurrencyPojo saveNewCurrencyDetails) {
        return sassControlPanelService.SaveCurrency(saveNewCurrencyDetails);
    }

    @RequestMapping(value = "/saveNewCustomer",
            method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public SassCustomer saveNewCustomer(@RequestBody SassCustomerPojo saveCustomerDetails) {
        return sassControlPanelService.SaveCustomer(saveCustomerDetails);
    }

    @RequestMapping(value = "/saveCountry", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse saveCountry(@RequestBody SassCountryPojo saveCountryDetails) {
        SassCountry SassCountry = sassControlPanelService.SaveCountryDetails(saveCountryDetails);
        return new IDMResponse(HttpStatus.OK.value(), " success", SassCountry);
    }

    @RequestMapping(value = "/saveProperty", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse saveProperty(@RequestBody PropertyPojo propertyPojo) {
        Property property = sassControlPanelService.SaveProperty(propertyPojo);
        return new IDMResponse(HttpStatus.OK.value(), " success", property);
    }

    @RequestMapping(value = "/savePaymentMethod", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public SassPaymentMethod savePaymentMethod(@RequestBody SassPaymentMethodPojo details) {
        SassPaymentMethod sassPaymentMethod = null;
        sassPaymentMethod = sassControlPanelService.savePaymentMethodList(details);
        return sassPaymentMethod;
    }

    @RequestMapping(value = "/getPayMethodList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPayMethodList() {
        List<SassPaymentMethodPojo> sassPaymentMethodPojoList = sassControlPanelService.PaymentMethodList();
        return new IDMResponse(HttpStatus.OK.value(), "success", sassPaymentMethodPojoList);
    }

    @RequestMapping(value = "/getItemList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getItemList() {
        List<IcItempojo> icItempojosList = sassControlPanelService.IcItemMethodList();
        return new IDMResponse(HttpStatus.OK.value(), "success", icItempojosList);
    }
    @RequestMapping(value = "/getSubscriptionListbasedonUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getSubscriptionListbasedonUser(@RequestAttribute String email) {
        return ResponseEntity.status(200).body(sassControlPanelService.getSubscriptionListbasedonUser(email));

    }
    @RequestMapping(value = "/getOrderListbasedemail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOrderListbasedemail(@RequestParam(value = "email") String email) {
        return ResponseEntity.status(200).body(sassControlPanelService.getSubscriptionListbasedonUser(email));

    }
    @RequestMapping(value = "/saveTicket",method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
        public ResponseEntity getTicket(@RequestBody TicketsPojo ticketpojo) throws Exception{
            Tickets tickets = null;
            tickets = sassControlPanelService.saveticket(ticketpojo);
            return ResponseEntity.status( 200).body( tickets);
        }

    @RequestMapping(value = "/getPropertyList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPropertyList() {
        List<PropertyPojo> propertyPojoList = sassControlPanelService.PropertyList();
        return new IDMResponse(HttpStatus.OK.value(), "success", propertyPojoList);
    }
    @RequestMapping(value = "/editProperty", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPropertyList(@RequestParam (value="name") String name) {
        List<PropertyPojo> propertyPojoList = sassControlPanelService.editProperty(name);
        return ResponseEntity.status( 200).body( propertyPojoList);
    }

    @RequestMapping(value = "/getCustomerList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getCustomerList(@RequestParam(value = "SearchText") String SearchText) {
        List<SassCustomerPojo> sassCustomerPojos = sassControlPanelService.CustomerList(SearchText);
        return new IDMResponse(HttpStatus.OK.value(), "success", sassCustomerPojos);
    }

    @RequestMapping(value = "/saveStateDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public IDMResponse savestate(@RequestBody SaasStatePojo saveStateDetails) {
        SaasState saasState = sassControlPanelService.SavestateDetails(saveStateDetails);
        return new IDMResponse(HttpStatus.OK.value(), " success", saasState);
    }

    @RequestMapping(value = "/stateList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse stateList() {
        List<SaasStatePojo> saasStatePojos = sassControlPanelService.stateList();
        return new IDMResponse(HttpStatus.OK.value(), "success", saasStatePojos);
    }

    @RequestMapping(value = "/getCountry",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getCountry() {
        List<SassCountryPojo> sassCountryPojos = sassControlPanelService.getCountryList();
        return new IDMResponse(HttpStatus.OK.value(), "success", sassCountryPojos);
    }
    @RequestMapping(value = "/getRenewList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getRenewList() {
        List<RenewPojo> renewList = sassControlPanelService.getRenewList();
        return new IDMResponse(HttpStatus.OK.value(), "success", renewList);
    }


    @RequestMapping(value = "/saveVersionControl", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public SassProjectModule saveVersionControl(@RequestBody SassProjectModulePojo modulePojo) {
        SassProjectModule sassProjectModule = null;
        sassProjectModule = sassControlPanelService.saveVersionControlList(modulePojo);
        return sassProjectModule;
    }

    @RequestMapping(value = "/getVersionControlList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getVersionControlList() {
        List<SassProjectModulePojo> sassProjectModule = sassControlPanelService.VersionControlList();
        return new IDMResponse(HttpStatus.OK.value(), "success", sassProjectModule);
    }

    @RequestMapping(value = "/saveNewSubscription", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public SassSubscriptions saveNewSubscription(@RequestBody SassSubscriptionsPojo subPojo) {
        SassSubscriptions sassSubscriptions = null;
        sassSubscriptions = sassControlPanelService.saveSubscriptionList(subPojo);
        return sassSubscriptions;
    }
    @RequestMapping(value = "/saveOrdersrenew", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public SassOrders saveOrdersrenew(@RequestBody SassOrdersPojo sassOrdersPojo) {
        SassOrders sassOrders = null;
        sassOrders = sassControlPanelService.saveOrdersrenew(sassOrdersPojo);
        return sassOrders;
    }


//
//    @RequestMapping(value = "/getSubscriptionByName",
//            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
//    public IDMResponse getSubscriptionByName(@RequestBody String subcriptionName){
//        List<SassSubscriptionsPojo> sassSubscriptionsPojoList= sassControlPanelService.SubscriptionByName(subcriptionName);
//        return new IDMResponse(HttpStatus.OK.value(), "azgar success",sassSubscriptionsPojoList);
//    }
@RequestMapping(value = "/editsubscription", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity editsubscription(@RequestParam(value = "id")Long id) {
    return ResponseEntity.status(200).body( sassControlPanelService.editsubscription(id));
}
@RequestMapping(value = "/editemail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity editemail(@RequestParam(value = "id")Long id) {
    return ResponseEntity.status(200).body( sassControlPanelService.editemail(id));
}
@RequestMapping(value = "/savepublish", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity savepublish(@RequestParam(value = "subscriptionId")Long id,
                                  @RequestParam(value = "status")String status) {
    return ResponseEntity.status(200).body( sassControlPanelService.savepublish(id,status));
}
    @RequestMapping(value = "/getSubscriptionList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getSubscriptionList(@RequestParam(value = "type",required = false)String type) {
        List<SassSubscriptionsPojo> sassSubscriptionsPojoList = sassControlPanelService.SubscriptionList(type);
        return new IDMResponse(HttpStatus.OK.value(), "azgar success", sassSubscriptionsPojoList);
    }
    @RequestMapping(value = "/getSubscriptionListByDeveloperId",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getSubscriptionListByDeveloperId(@RequestParam(value = "code")String code) {
        List<SassSubscriptionsPojo> sassSubscriptionsPojoList = sassControlPanelService.getSubscriptionListByDeveloperId(code);
        return new IDMResponse(HttpStatus.OK.value(), "azgar success", sassSubscriptionsPojoList);
    }
    @RequestMapping(value = "/getAccountList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccountList(@RequestParam(value = "devcode")String code) {
    return ResponseEntity.status( 200).body( sassControlPanelService.getAccountList(code));
    }

//    @RequestMapping(value = "/getPaginatedSubscriptionList", method = RequestMethod.POST, produces = "application/json")
//    public ResponseEntity getPaginatedSubscriptionList(@RequestParam(value = "searchText", required = false) String searchText,
//                                                   @RequestBody BasePojo basePojo) {
//            return ResponseEntity.status(200).body(sassControlPanelService.getPaginatedSubscriptionList(basePojo, searchText));
//    }

    @RequestMapping(value = "/saveNewPackage", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public SassPackages saveNewPackage(@RequestBody SassPackagesPojo pkgPojo) {
        SassPackages sassPackages = null;
        sassPackages = sassControlPanelService.savePackageList(pkgPojo);
        return sassPackages;
    }

    @RequestMapping(value = "/getPackageList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPackageList() {
        List<SassPackagesPojo> sassPackagesPojos = sassControlPanelService.PackagesList();
        return new IDMResponse(HttpStatus.OK.value(), "success", sassPackagesPojos);
    }

    @RequestMapping(value = "/getPermissionMasterList", method = RequestMethod.GET, produces = "application/json")
    public List<PermissionMaster> getPermissionMasterList() {
        return sassControlPanelService.PermissionMasterList();
    }

    @RequestMapping(value = "/savepayment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public IDMResponse savepayment(@RequestBody PaymentTypePojo saveDetails)throws Exception {
        PaymentTypePojo PosPaymentTypes = sassControlPanelService.Savespayments(saveDetails);
        return new IDMResponse(HttpStatus.OK.value(), " success", PosPaymentTypes);
    }

    @RequestMapping(value = "/paymentList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse paymentList() {
        List<PaymentTypePojo> PaymentTypePojo = sassControlPanelService.paymenTtypeList();
        return new IDMResponse(HttpStatus.OK.value(), "success", PaymentTypePojo);
    }
    @RequestMapping(value = "/getOperatorList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getOperatorList() {
        List<Operatorpojo> operatorpojoList = sassControlPanelService.getOperatorList();
        return new IDMResponse(HttpStatus.OK.value(), "success", operatorpojoList);
    }
    @RequestMapping(value = "/getValidatorList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getValidatorList() {
        List<ValidatorPojo> validatorPojoList = sassControlPanelService.getValidatorList();
        return new IDMResponse(HttpStatus.OK.value(), "success", validatorPojoList);
    }
    @RequestMapping(value = "/getPositionList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPositionList() {
        List<PositionPojo> positionList = sassControlPanelService.getPositionList();
        return new IDMResponse(HttpStatus.OK.value(), "success", positionList);
    }
    @RequestMapping(value = "/getActionList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getActionList() {
        List<ActionPojo> actionPojoList = sassControlPanelService.getActionList();
        return new IDMResponse(HttpStatus.OK.value(), "success", actionPojoList);
    }
    @RequestMapping(value = "/getApplicationList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getApplictionList() {
        List<ApplicationPojo> applicationPojoList = sassControlPanelService.getApplicationList();
        return new IDMResponse(HttpStatus.OK.value(), "success", applicationPojoList);
    }
    @RequestMapping(value = "/getdevApplicationList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getdevApplicationList(@RequestParam(value = "code")String code) {
        List<ApplicationPojo> applicationPojoList = sassControlPanelService.getdevApplicationList(code);
        return new IDMResponse(HttpStatus.OK.value(), "success", applicationPojoList);
    }

    @RequestMapping(value = "/customernotifyList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse customernotifyList(@RequestParam(value = "typedoc")String type) throws Exception {
        List<CustomerNotificationsPojo> custinfo = sassControlPanelService.customernotify(type);
        return new IDMResponse(HttpStatus.OK.value(), "success", custinfo);
    }

    @RequestMapping(value = "/getpaginatednotificationList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getpaginatednotificationList( @RequestBody BasePojo basePojo,
                                                        @RequestParam(value = "searchText") String searchText,
                                                        @RequestParam(value = "restaurantName") String restaurantName
                                                        ) {
        return ResponseEntity.status(200).body(sassControlPanelService.getpaginatednotificationList(basePojo, searchText,restaurantName));
    }

    @RequestMapping(value = "/transdataList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse transdataList() {
        List<TransactionsDataPojo> data = sassControlPanelService.transactiondataList();
        return new IDMResponse(HttpStatus.OK.value(), "success", data);
    }

    @RequestMapping(value = "/savepractitioner", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public IDMResponse savepractitioner(@RequestBody PractitionerRegistrPojo saveDetails) {
        PractitionerRegistration register = sassControlPanelService.SavepracReg(saveDetails);
        return new IDMResponse(HttpStatus.OK.value(), " success", register);
    }


    @RequestMapping(value = "/savePktFieldsPermission", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    public PktFields savePktFieldsPermission(@RequestBody PktFieldsPojo saveNewPktFeilds )  {
        return sassControlPanelService.SavePktFields(saveNewPktFeilds);
    }

     @RequestMapping(value = "/saveValidator", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    public Validator saveValidator(@RequestBody ValidatorPojo validatorPojo )  {
        return sassControlPanelService.saveValidator(validatorPojo);
    }
     @RequestMapping(value = "/savePosition", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    public Position savePosition(@RequestBody PositionPojo positionPojo )  {
        return sassControlPanelService.savePosition(positionPojo);
    }

    @RequestMapping(value = "/saveOperator", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    public Operator saveOperator(@RequestBody Operatorpojo operatorpojo )  {
        return sassControlPanelService.saveOperator(operatorpojo);
    }

    @RequestMapping(value = "/saveApplication", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    public Application saveApplication(@RequestBody ApplicationPojo applicationPojo )  {
        return sassControlPanelService.saveApplication(applicationPojo);
    }

    @RequestMapping(value = "/permissionfieldList",
            method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse permissionfieldList(){
        List<PktFieldsPojo> pktFieldsPojoList= sassControlPanelService.permissionfieldList();
        return new IDMResponse(HttpStatus.OK.value(), "success",pktFieldsPojoList);
    }

    @RequestMapping(value = "/practitionerReg",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse practitionerReg(@RequestParam(value = "SearchText") String SearchText) {

        List<PractitionerRegistrPojo> reg = sassControlPanelService.practitionerReg(SearchText);
        return new IDMResponse(HttpStatus.OK.value(), "success", reg);
    }

    @RequestMapping(value = "/saveNewAddOn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public IDMResponse saveNewAddOn(@RequestBody AddOnnPojo addOnnPojo) {
        AddOnn addOnn = sassControlPanelService.SaveAddOn(addOnnPojo);
        return new IDMResponse(HttpStatus.OK.value(), " success", addOnn);
    }

    @RequestMapping(value = "/saveCartCustomer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public IDMResponse saveCartCustomer(@RequestBody CartCustomerLinkPojo cartCustomerLinkPojo)throws Exception {
        CartCustomerLink cartCustomerLink = sassControlPanelService.SaveCartCustomer(cartCustomerLinkPojo);
        return new IDMResponse(HttpStatus.OK.value(), " success", cartCustomerLink);
    }

    @RequestMapping(value = "/getCartCustomerList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getCartCustomerList(@RequestParam(value = "SearchText") String SearchText) {

        List<CartCustomerLinkPojo> cartCustomerLinkPojos = sassControlPanelService.CartCustomerList(SearchText);
        return new IDMResponse(HttpStatus.OK.value(), " success", cartCustomerLinkPojos);
    }

    @RequestMapping(value = "/getAddOnList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getAddOnList() {
        List<AddOnnPojo> addOnnPojos = sassControlPanelService.AddOnList();
        return new IDMResponse(HttpStatus.OK.value(), " success", addOnnPojos);
    }

    @RequestMapping(value = "/savepractitionerorders", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public IDMResponse savepractitionerorders(@RequestBody PractitionerordersPojo saveDetails) {
        Practitionerorders register = sassControlPanelService.Savepracorders(saveDetails);
        return new IDMResponse(HttpStatus.OK.value(), " success", register);
    }

    @RequestMapping(value = "/pracorderList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse pracorderList() {
        List<PractitionerordersPojo> order = sassControlPanelService.practorderList();
        return new IDMResponse(HttpStatus.OK.value(), "success", order);
    }

    @RequestMapping(value = "/saveCartRegistration", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public IDMResponse saveCartRegistration(@RequestBody CartRegistrationPojo saveCartRegDetails) {
        Cartregistration cartregistration = sassControlPanelService.saveCartRegistration(saveCartRegDetails);
        return new IDMResponse(HttpStatus.OK.value(), " success", cartregistration);
    }

    @RequestMapping(value = "/getCartRegList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getCartRegList() {
        List<CartRegistrationPojo> cartRegistrationPojos = sassControlPanelService.cartRegistrationPojoList();
        return new IDMResponse(HttpStatus.OK.value(), " success", cartRegistrationPojos);
    }

    @RequestMapping(value = "/getPermissionRights", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPermissionRights() {
        HashMap<Object, Object> companyAccessRightsDTO = sassControlPanelService.retriveCompanyPermissions();
        return ResponseEntity.status(200).body(companyAccessRightsDTO);
    }

    @RequestMapping(value = "/savedestination", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse savedestination(@RequestBody DestinationTypePojo savedestination) {
        DestinationType destination = sassControlPanelService.SaveDestination(savedestination);
        return new IDMResponse(HttpStatus.OK.value(), " success", destination);
    }

    @RequestMapping(value = "/saveNewItem",
            method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public IcItem saveNewItem(@RequestBody IcItempojo saveItemDetails) {
        return sassControlPanelService.SaveItem(saveItemDetails);
    }

    @RequestMapping(value = "/detinationtypeList{sourceType}",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse detinationtypeList(@PathVariable String sourceType) {
        List<DestinationTypePojo> desttype = sassControlPanelService.destinationList(sourceType);
        return new IDMResponse(HttpStatus.OK.value(), "success", desttype);
    }

    @RequestMapping(value = "/detinationtypeList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse detinationtypeList() {
        List<DestinationTypePojo> desttype = sassControlPanelService.destinationtype();
        return new IDMResponse(HttpStatus.OK.value(), "success", desttype);
    }

    @RequestMapping(value = "/savedestinationmap", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse savedestinationmap(@RequestBody DestinationMapPojo savedestination) {
        DestinationMap destination = sassControlPanelService.SaveDestinationMap(savedestination);
        return new IDMResponse(HttpStatus.OK.value(), " success", destination);
    }

    @RequestMapping(value = "/detinationtypeMapList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse detinationtypeMapList() {
        List<DestinationMapPojo> destmap = sassControlPanelService.destinationMapList();
        return new IDMResponse(HttpStatus.OK.value(), "success", destmap);
    }

    @RequestMapping(value = "/saveRTRSyncDetails", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse saveRTRSyncDetails(@RequestBody RTRSyncSettingsPojo rtrSyncSettingsPojo) {
        RTRSyncSettings rtrSyncSettings = sassControlPanelService.saveRTRSyncsettingDetails(rtrSyncSettingsPojo);
        return new IDMResponse(HttpStatus.OK.value(), " success", rtrSyncSettings);
    }

    @RequestMapping(value = "/getRTRSyncList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getRTRSyncList(@RequestParam(value = "SearchText") String SearchText) {
        List<RTRSyncSettingsPojo> rtrSyncsettingList = sassControlPanelService.getRTRSyncsettingList(SearchText);
        return new IDMResponse(HttpStatus.OK.value(), "success", rtrSyncsettingList);
    }

    @RequestMapping(value = "/getemailList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getmailList() {
        List<EmailReaderPojo> emaildetails = sassControlPanelService.emailList();
        return new IDMResponse(HttpStatus.OK.value(), "success", emaildetails);
    }

    @RequestMapping(value = "/saveemaildetails", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse saveemaildetails(@RequestBody EmailReaderPojo emaildetails) {
        EmailReader saveemail = sassControlPanelService.Saveemail(emaildetails);
        return new IDMResponse(HttpStatus.OK.value(), " success", saveemail);
    }
    @RequestMapping(value = "/savesubscriptionemail", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse savesubscriptionemail(@RequestBody SassSubscriptionsPojo sassSubscriptionsPojo) {
        SassSubscriptions sassSubscriptions = sassControlPanelService.savesubscriptionemail(sassSubscriptionsPojo);
        return new IDMResponse(HttpStatus.OK.value(), " success", sassSubscriptions);
    }

    @RequestMapping(value = "/savepermissiongroup", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse savepermissiongroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        PermissionGroup savepermissiongroup = sassControlPanelService.savepermissionGroup(permissionGroupDTO);
        return new IDMResponse(HttpStatus.OK.value(), " success", savepermissiongroup);
    }

    @RequestMapping(value = "/getFirstLevelpermissionMaster",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getFirstLevelpermissionMaster() {
        List<PermissionMasterDTO> level1 = sassControlPanelService.getFirstLevelpermissionMaster1();
        return new IDMResponse(HttpStatus.OK.value(), "success", level1);
    }

    @RequestMapping(value = "/pktImportSave", method = RequestMethod.POST)
    public ResponseEntity pktImportSave(@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row != null){
                    org.apache.poi.ss.usermodel.Cell tableName = row.getCell(0);
                    org.apache.poi.ss.usermodel.Cell fieldName = row.getCell(1);
                    org.apache.poi.ss.usermodel.Cell groupOf = row.getCell(2);
                    org.apache.poi.ss.usermodel.Cell status = row.getCell(3);

                    PktFieldsPojo pktFieldsPojo = new PktFieldsPojo();
                    pktFieldsPojo.setFieldName(tableName == null ? null : tableName.toString());
                    pktFieldsPojo.setTableName(fieldName == null ? null : fieldName.toString());
                    pktFieldsPojo.setGroupOf(groupOf == null ? null : groupOf.toString());
                    pktFieldsPojo.setStatus(status == null ? null : status.toString());
                    sassControlPanelService.SavePktFields(pktFieldsPojo);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }




    //get TableNames in PKTFields
    @RequestMapping(value = "/getPktTablepermissionMaster",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPktTablepermissionMaster(@RequestParam("subscriptionName") String subscriptionName) {
        List<PktFieldsPojo> level1 = sassControlPanelService.getPktTablepermissionMaster(subscriptionName);
        return new IDMResponse(HttpStatus.OK.value(), "success", level1);
    }


    //getAllTableNameListInPktFields
    @RequestMapping(value = "/getAllTableNameListInPktFields",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getAllTableNameListInPktFields() {
        List<PktFieldsPojo> level1 = sassControlPanelService.getAllTableNameListInPktFields();
        return new IDMResponse(HttpStatus.OK.value(), "success", level1);
    }


    @RequestMapping(value = "/getPktFieldspermissionMaster",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPktFieldspermissionMaster(@RequestParam("tableName") String tableName) {
        List<PktFieldsPojo> level1 = sassControlPanelService.getPktFieldspermissionMaster(tableName);
        return new IDMResponse(HttpStatus.OK.value(), "success", level1);
    }
 @RequestMapping(value = "/getPktpermissionMaster",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPktpermissionMaster(@RequestParam("tableName") String tableName) {
        List<PktPermissionPojo> level1 = sassControlPanelService.getPktpermissionMaster(tableName);
        return new IDMResponse(HttpStatus.OK.value(), "success", level1);
    }

    //pKT Fields lIst
    @RequestMapping(value = "/permissionTableFieldsNameList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse permissionTableFieldsNameList(@RequestParam("tableName") String tableName) {
        List<PktFieldsPojo> level1 = sassControlPanelService.permissionTableFieldsNameList(tableName);
        return new IDMResponse(HttpStatus.OK.value(), "success", level1);
    }
    @RequestMapping(value = "/getsecondLevelpermissionMaster2",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getsecondLevelpermissionMaster2(@RequestParam(value = "pmasterId") PermissionMaster permissionmasterId) {
        List<PermissionMasterDTO> level2 = sassControlPanelService.getsecondLevelpermissionMaster1(permissionmasterId);
        return new IDMResponse(HttpStatus.OK.value(), "success", level2);
    }

    @RequestMapping(value = "/permissionFieldsNameList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse permissionFieldsNameList(@RequestParam(value = "Id") PktFields Id) {
        List<PktFieldsPojo> level2 = sassControlPanelService.permissionFieldsNameList(Id);
        return new IDMResponse(HttpStatus.OK.value(), "success", level2);
    }

    //PKT
    @RequestMapping(value = "/permissionGroupOffList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse permissionGroupOffList(@RequestParam(value = "Id") PktFields Id) {
        List<PktFieldsPojo> level2 = sassControlPanelService.permissionGroupOffList(Id);
        return new IDMResponse(HttpStatus.OK.value(), "success", level2);
    }

    @RequestMapping(value = "/getTablesNameList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getTablesNameList(@RequestParam(value = "subscriptionName") PktFields subscriptionName) {
        List<PktFieldsPojo> level2 = sassControlPanelService.getTablesNameList(subscriptionName);
        return new IDMResponse(HttpStatus.OK.value(), "success", level2);
    }

    @RequestMapping(value = "/getPermissionmasterLists",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPermissionmasterLists() {
        List<PermissionMasterDTO> permission = sassControlPanelService.getpermissionMasterList();
        return new IDMResponse(HttpStatus.OK.value(), "success", permission);
    }

    @RequestMapping(value = "/savepermissionmaster", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse savepermissionmaster(@RequestBody PermissionMasterDTO permissionMasterDTO) {
        PermissionMaster permissionMaster = sassControlPanelService.savepermissionmaster(permissionMasterDTO);
        return new IDMResponse(HttpStatus.OK.value(), "success", permissionMaster);
    }

    @RequestMapping(value = "/saveeditpermissionmaster", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse saveeditpermissionmaster(@RequestBody PermissionMasterDTO permissionMasterDTO) {
        PermissionMaster permissionMaster = sassControlPanelService.saveeditpermissionmaster1(permissionMasterDTO);
        return new IDMResponse(HttpStatus.OK.value(), "success", permissionMaster);
    }

    @RequestMapping(value = "/deletepermission", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse deletepermission(@RequestBody PermissionMasterDTO permissionMasterDTO) {
        PermissionMaster permissionMaster = sassControlPanelService.deletepermissionmaster(permissionMasterDTO);
        return new IDMResponse(HttpStatus.OK.value(), "success", permissionMaster);
    }

    @RequestMapping(value = "/deletepermissiongroup", method = RequestMethod.POST, consumes = "application/json")
    public IDMResponse deletepermissiongroup(@RequestBody PermissionGroupDTO permissionGroupDTO) {
        PermissionGroup permissiongroup = sassControlPanelService.deletegroup(permissionGroupDTO);
        return new IDMResponse(HttpStatus.OK.value(), "success", permissiongroup);
    }

    @RequestMapping(value = "/savePktPermsission", method = RequestMethod.POST, produces = "application/json")
    public IDMResponse savePktPermsission(@RequestBody PktPermissionPojo pktPermissionPojo) {
        PktPermission pktPermission = sassControlPanelService.savePktPermsissionTable(pktPermissionPojo);
        if (pktPermission == null) {
            return null;
        } else {
            return new IDMResponse(HttpStatus.OK.value(), "success", pktPermission);
        }
    }

    @RequestMapping(value = "/savePermsission", method = RequestMethod.POST, produces = "application/json")
    public IDMResponse savePermsission(@RequestBody PermissionPojo permissionPojo) {
        Permission permission = sassControlPanelService.savePermsission(permissionPojo);
        return new IDMResponse(HttpStatus.OK.value(), "success", permission);
    }


    @RequestMapping(value = "/saveColumnType", method = RequestMethod.POST, produces = "application/json")
    public IDMResponse saveColumnType(@RequestBody ColumnTypePojo  columnTypePojo) {
        ColumnType columnType = sassControlPanelService.saveColumnType(columnTypePojo);
        return new IDMResponse(HttpStatus.OK.value(), "success", columnType);
    }


    //Save PKT fields####
    @RequestMapping(value = "/savePktFieldsPermsission", method = RequestMethod.POST, produces = "application/json")
    public IDMResponse savePktFieldsPermsission(@RequestBody PktPermissionPojo pktPermissionPojo) {
        PktPermission pktPermission = sassControlPanelService.savePktPermsissionTableColumnMapping(pktPermissionPojo);
        if (pktPermission == null) {
            return null;
        } else {
            return new IDMResponse(HttpStatus.OK.value(), "success", pktPermission);
        }
    }


    //Save PKT fields####
    @RequestMapping(value = "/saveWebPermsission", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    public String saveWebPermsission(@RequestBody String webpermission)throws Exception {
        String data =sassControlPanelService.saveWebPermission(webpermission);
        return data;

    }





    @RequestMapping(value = "/getPktPermisssionList",
            method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPktPermisssionList(@RequestParam ("subscription") String subscription){
        List<PktPermissionPojo>pktPermisssionList=sassControlPanelService.getPktPermisssionList(subscription);

        return new IDMResponse(HttpStatus.OK.value(),"success",pktPermisssionList);
    }



    @RequestMapping(value = "/getColumnPermisssionList",
            method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getColumnPermisssionList(){
        List<PktPermissionPojo>pktPermisssionList=sassControlPanelService.getColumnPermisssionList();

        return new IDMResponse(HttpStatus.OK.value(),"success",pktPermisssionList);
    }

    @RequestMapping(value = "/getPaginatedFieldList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedFieldList(@RequestParam(value = "type", required = false) String status,
                                                @RequestParam(value = "searchText")String searchText,
                                                @RequestParam(value = "tablesearchText")String tablesearchText,
                                                @RequestParam(value = "keysearchText")String keysearchText,
                                                @RequestParam(value = "columnsearchText")String columnsearchText,
                                                @RequestParam(value = "operatorsearchText")String operatorsearchText,
                                                @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(sassControlPanelService.getPaginatedfieldList(status,basePojo,searchText,tablesearchText,keysearchText,columnsearchText,operatorsearchText));
    }

    @RequestMapping(value = "/getAllColumnTypeList",
            method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getAllColumnTypeList(){
        List<ColumnTypePojo> columnTypeList=sassControlPanelService.getAllColumnTypeList();
        return new IDMResponse(HttpStatus.OK.value(),"success",columnTypeList);
    }


    @RequestMapping(value = "/getTableNameList",
            method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getTableNameList(@RequestParam("subscriptionName") String subscriptionName){
        List<PktPermissionPojo>pktPermisssionList=sassControlPanelService.getTableNameList(subscriptionName);

        return new IDMResponse(HttpStatus.OK.value(),"success",pktPermisssionList);
    }

    @RequestMapping(value = "/getAllPermissionList",method = RequestMethod.POST,produces =MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getAllPermissionList(){
        List<PermissionPojo> permissionPojoList = sassControlPanelService.getAllPermissionList();
        return  new IDMResponse(HttpStatus.OK.value(),"success",permissionPojoList);
    }

    @RequestMapping(value = "/permissionTableNameLists",method = RequestMethod.POST,produces =MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse permissionTableNameLists(@RequestParam("tableName") String tableName,
                                                @RequestParam("application") String application){
        List<PktPermissionBean> permissionPojoList = sassControlPanelService.permissionTableNameLists(tableName,application);
        return  new IDMResponse(HttpStatus.OK.value(),"success",permissionPojoList);
    }

    @RequestMapping(value = "/getTableCodeList",method = RequestMethod.POST,produces =MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getTableCodeList(@RequestParam("keyValue") String keyValue,
                                        @RequestParam("keySubscription") String keySubscription) throws JSONException {
        List<PktFieldsPojo> pktFieldsPojos = sassControlPanelService.getTableCodeList(keyValue,keySubscription);
        return  new IDMResponse(HttpStatus.OK.value(),"success",pktFieldsPojos);
    }
    //get Table Name List In Pkt Permission
    @RequestMapping(value = "/getTableNameLists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getTableNameLists(@RequestParam("keyName") String keyName) {
        List<PktPermissionPojo> pktPermissionPojos = sassControlPanelService.getTableNameLists(keyName);
        return new IDMResponse(HttpStatus.OK.value(), "success", pktPermissionPojos);
    }
    @RequestMapping(value = "/getPermissionmasterLevels",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getPermissionmasterLevels(@RequestParam(value = "permissionMasterId") Long permissionmasterId) {
        PermissionMaster permissionMaster = sassControlPanelService.getPermissionMasterById(permissionmasterId);
        return new IDMResponse(HttpStatus.OK.value(), "success", permissionMaster);
    }

    @RequestMapping(value = "/getPaginatedOperatorList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedOperatorList(@RequestParam(value = "searchText", required = false) String searchText,
                                                   @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(sassControlPanelService.getPaginatedOperatorList(basePojo, searchText));
    }

    @RequestMapping(value = "/getPaginatedActionList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedActionList(@RequestParam(value = "searchText", required = false) String searchText,
                                                   @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(sassControlPanelService.getPaginatedActionList(basePojo, searchText));
    }

    @RequestMapping(value = "/getPaginatedtableList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedtableList(@RequestParam(value = "searchText") String searchText,
                                                @RequestParam(value = "searchText1") String searchText1,
                                                @RequestParam(value = "searchText2") String searchText2,
                                                @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(sassControlPanelService.getPaginatedTableList(basePojo,searchText,searchText1,searchText2));
    }
    @RequestMapping(value="/saveEmailServer",method=RequestMethod.POST,produces ="application/json")
    public IDMResponse saveEmailServer(@RequestBody EmailServerPojo server){
        EmailServer email=sassControlPanelService.saveEmailServer(server);
        return new IDMResponse(HttpStatus.OK.value(),"success",email);

    }
    @RequestMapping(value = "/getemailserverList",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getemailserverList() {
        List<EmailServerPojo> pojo = sassControlPanelService.getemailserverlist();
        return new IDMResponse(HttpStatus.OK.value(), "success", pojo);
    }
    @RequestMapping(value = "/editMail", method = RequestMethod.POST, produces = "application/json")
    public IDMResponse editMail(@RequestParam(value = "userName", required = false) String userName) {
        EmailServerPojo email=sassControlPanelService.editMaill(userName);
        return new IDMResponse(HttpStatus.OK.value(),"success",email);
    }
    @RequestMapping(value = "/deleteMail", method = RequestMethod.POST, produces = "application/json")
    public void deleteMail(@RequestParam(value = "userName", required = false) String userName) {
        sassControlPanelService.deleteMail(userName);
    }


    @RequestMapping(value = "/saveModules",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse saveModules(@RequestBody String application)throws Exception {
        PktBuilder pktBuilder =sassControlPanelService.saveModules(application);
            return new IDMResponse(HttpStatus.OK.value(), "success", pktBuilder);

    }
    @RequestMapping(value = "/getpktbuilderByApplication",
            method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getpktbuilderByApplication(@RequestParam(value = "application")String code) {
        return new IDMResponse(HttpStatus.OK.value(), "success", sassControlPanelService.getpktbuilderbyApplication(code));
    }


    @RequestMapping(value = "/getpktchildByApplication",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public IDMResponse getpktchildByApplication(@RequestParam(value ="application")String applicationName, @RequestParam(value ="menuName")String menuName) throws JSONException {
        return new IDMResponse(HttpStatus.OK.value(), "success",sassControlPanelService.getpktbuilderValuesbyApplication(applicationName,menuName));
    }

    @RequestMapping(value = "/deletePermissions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String deletePermissions(@RequestParam(value ="permissionId")String id)
            throws JSONException {
        sassControlPanelService.deletePermissions(id);
        return "Success";
    }


    @RequestMapping(value = "/getwebpermissionLists",
            method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public IDMResponse getwebpermissionLists(@RequestBody String list)throws Exception {
        List<WebPermissionPojo> webpermissions = sassControlPanelService.getwebpermissionList(list);
        return new IDMResponse(HttpStatus.OK.value(), "success", webpermissions);
    }

}
