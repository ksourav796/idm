app.controller('fieldsCtrl',
    function ($scope, $rootScope, $http, $location, $filter, Notification) {
        $scope.hiposServerURL = "/pkt";
        $scope.hiposServerURLS = "/services";
        $scope.inactiveStatus = "Active";
        app.config(['$qProvider', function ($qProvider) {
            $qProvider.errorOnUnhandledRejections(false);
        }]);

        $scope.removetableType = function () {
            $scope.pktPermissionId = "";
            $scope.keySubscription = "";
            $scope.keyName = "";
            $scope.keyValue = "";
            $scope.keyReferenceText = "";
            $scope.keyVisibilityText = "";
            $scope.action = "";
            $scope.fieldName = "";
            $scope.placeHolder = "";
            $scope.validation = "";
            $scope.priority = "";
            $scope.sync = "";
            $scope.descriptor = "";
            $scope.pktpermission = "";
            $scope.status = "";
            $scope.column = "";
            $scope.value = "";
            $scope.roundOff = "";
            $scope.columnName = "";
            $scope.nameo = "";
            $scope.operation = "";
            $scope.namev = "";
            $scope.input = "";
            $scope.namea = "";
            $scope.namep = "";
            $scope.pktPermissionId = "";
            $scope.keyVisibility = "";
        };
        //getAllColumn Position

        $scope.getPositionList = function () {

            $http.post($scope.hiposServerURL + "/getPositionLists").then(function (response) {
                var data = response.data.object;
                $scope.positiontype = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getPositionList();

        //getAllOpertorList
        // $scope.getOperatorList = function () {
        //
        //     $http.post($scope.hiposServerURL + "/getOperatorLists").then(function (response) {
        //         var data = response.data.object;
        //         $scope.operatortype = data;
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        // };
        // $scope.getOperatorList();


        //getAllValidatorList
        $scope.getValidatorList = function () {

            $http.post($scope.hiposServerURL + "/getValidatorLists").then(function (response) {
                var data = response.data.object;
                $scope.validatortype = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getValidatorList();
        $scope.getApplicationList = function () {

            $http.post($scope.hiposServerURL + "/getApplicationLists").then(function (response) {
                var data = response.data.object;
                $scope.applicationtype = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getApplicationList();

        //getAllActionList
        $scope.getActionLists = function () {

            $http.post($scope.hiposServerURL + "/getActionLists").then(function (response) {
                var data = response.data.object;
                $scope.actiontype = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getActionLists();

        //get tableNameIn Pkt Fields Save In PKT Permisssion Master
        $scope.getTableList = function (subscriptionName) {
            $(".loader").css("display", "block");
            $http.post('/services' + '/getTableNameList?subscriptionName=' + subscriptionName).then(function (response) {
                var data = response.data.object;
                $scope.tableNameList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                })
            });
        };
        $scope.getTableList();

        $scope.getPktPermisssionListPKt = function (keySubscription) {

            $http.post($scope.hiposServerURL + "/getPktPermisssionList?subscription=" + $scope.keySubscription).then(function (response) {
                var data = response.data.object;
                $scope.tableNameListOnPkt = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getPktPermisssionListPKt();

        $scope.getoperatorlistbasedonapplication = function (keySubscription) {

            $http.post($scope.hiposServerURL + "/getOperatorListBasedOnApplication?subscription=" + keySubscription).then(function (response) {
                var data = response.data.object;
                $scope.operatelist = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        // $scope.getoperatorlistbasedonapplication();

        //Get PKT FieldsName In PKT Fields Table fieldName
        // $scope.getFieldsNameList = [];
        $scope.getFieldsNameList = function () {
            $(".loader").css("display", "block");
            if ($scope.keyValue != null && $scope.keySubscription != null ) {
                $http.post('/pkt' + '/getTableCodeLists?keyValue=' + $scope.keyValue + '&keySubscription=' + $scope.keySubscription).then(function (response) {
                    var data = response.data.object;
                    $scope.tableFieldNameList = data;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    })
                });
            }
        };
        // $scope.getFieldsNameList();

        $scope.getAllColumnTypeList = function () {
            $(".loader").css("display", "block");
            $http.post('/pkt' + '/getAllColumnTypeLists').then(function (response) {
                var data = response.data.object;
                $scope.columnTypeList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                })
            });
        };
        $scope.getAllColumnTypeList();


        $scope.getTableNameLists = function (keyName) {
            $(".loader").css("display", "block");

            // $scope.tableCode = table.keyName;
            $scope.namea = "";
            $http.post('/services' + '/getTableNameLists?keyName=' + $scope.keyName).then(function (response) {
                var data = response.data.object;
                $scope.tableFieldNameList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                })
            });
        };
        // $scope.getTableNameLists();
        $scope.getfromcolumnlist = function (data) {
            $http.post("/pkt/getPktFieldspermissionMasters?tableName=" + data).then(function (response) {
                var data = response.data.object;
                $scope.fromcolumnlist = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }


        $scope.gettocolumnlist = function(){
            $http.post("/pkt/getPktFieldspermissionMasters?tableName=" + $scope.tablecode).then(function (response) {
                var data = response.data.object;
                $scope.tocolumnlist = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }

        $scope.getSubscriptionList = function () {
            $(".loader").css("display", "block");
            $http.post($scope.hiposServerURLS + "/getSubscriptionList").then(function (response) {
                var data = response.data.object;
                console.log(data);
                $scope.subscriptionList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getSubscriptionList();

        $scope.editTable = function (data) {
            $scope.pktPermissionId = data.pktPermissionId;
            $scope.keySubscription = data.keySubscription;
            $scope.fieldName = data.keyName;
            $scope.keyName = data.keyGroup;
            $scope.keyValue = data.tableName;
            $scope.action = data.keyAction;
            $scope.placeHolder = data.keyPlaceHolder;
            $scope.validation = data.keyValidation;
            $scope.priority = data.keyPriority;
            $scope.sync = data.sync;
            $scope.descriptor = data.descriptor;
            $scope.pktpermission = data.permission;
            $scope.status = data.status;
            $scope.column = data.columnPosition;
            $scope.value = data.keyValue;
            $scope.roundOff = data.roundOff;
            $scope.columnName = data.columnName;
            $scope.nameo = data.operator;
            $scope.operation = data.operation;
            $scope.namev = data.keyValidation;
            $scope.inputypes = data.inputType;
            $scope.namea = data.keyAction;
            $scope.namep = data.columnPosition;
            $scope.keyReference = data.keyReference;
            $scope.status = data.status,
                $scope.keyVisibility = data.keyVisibility
            if (data.operator != null) {
                if (data.keyAction.contains("Save")) {
                    $scope.saveOpe = true;
                    $scope.EditOpe = false;
                    $scope.ListOpe = false;
                    $scope.AddOpe = false;
                }
                else if (data.keyAction.contains("Add")) {
                    $scope.AddOpe = true;
                    $scope.saveOpe = false;
                    $scope.ListOpe = false;
                    $scope.AddOpe = false;
                }
                else if (data.keyAction.contains("Edit")) {
                    $scope.EditOpe = true;
                    $scope.saveOpe = false;
                    $scope.ListOpe = false;
                    $scope.AddOpe = false;
                }
                else if (data.keyAction.contains("List")) {
                    $scope.ListOpe = true;
                    $scope.saveOpe = false;
                    $scope.EditOpe = false;
                    $scope.AddOpe = false;
                }
            } else {
                $scope.saveOpe = false;
                $scope.EditOpe = false;
                $scope.ListOpe = false;
                $scope.AddOpe = false;
            }
            $scope.operation = 'Edit';


            $scope.getPktPermisssionListPKt($scope.keySubscription);
            $scope.getoperatorlistbasedonapplication($scope.keySubscription);
            $scope.getFieldsNameList($scope.keyValue);
            $scope.getActionLists($scope.namea);
            $('#cust-notif').text("Edit Table Column Mapping");
            $('#submit').text("UpDate");
            $("#add_new_pkt_Table_modal").modal('show');
        }

        $scope.getPktPermisssionListPKts = function (opeartion) {
            $scope.keySubscription=opeartion.keySubscription;
            $http.post($scope.hiposServerURL + "/getPktPermisssionList?subscription=" + $scope.keySubscription).then(function (response) {
                var data = response.data.object;
                opeartion.tableNameListOnPkts = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        // $scope.getPktPermisssionListPKts();

        $scope.getFieldsNameLists = function (operation) {
            $(".loader").css("display", "block");
            $scope.keyValue=operation.keyValue;
            if ($scope.keyValue != null && $scope.keySubscription != null) {
                $http.post('/pkt' + '/getTableCodeLists?keyValue=' + $scope.keyValue + '&keySubscription=' + $scope.keySubscription).then(function (response) {
                    var data = response.data.object;
                    operation.tableFieldNameLists = data;
                    $scope.tableFieldNameLists = data;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    })
                });
            }
        };
        // $scope.getFieldsNameLists();


        //getAllPktFieldsListColumCodeList
        $scope.getAllPktFieldsList = function () {
            $(".loader").css("display", "block");
                $http.post('/pkt' + '/getAllPktFieldsLists').then(function (response) {
                    var data = response.data.object;
                    $scope.getAllPktFieldsLists = data;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    })
                });
        };
        $scope.getAllPktFieldsList();

        $scope.getList = function () {
            $(".loader").css("display", "block");
            $http.post('/pkt' + '/getAllPktPermissionList').then(function (response) {
                var data = response.data.object;
                $scope.allPktPermissionList = data;
                $scope.actionlist=[];
                $scope.operatorList=[];
                angular.forEach($scope.allPktPermissionList,function(val,key){
                    $scope.val = {};
                    if(val.operator!=null) {
                        if (val.operator.contains("List")) {
                            if ($scope.operatorList.indexOf(val.operator) == -1) {
                                $scope.operatorList.push(val.operator);
                            }

                        }
                    }
                     if(val.keyAction!=null){
                        if (val.keyAction.contains("List")) {
                            if ($scope.actionlist.indexOf(val.keyAction) == -1) {
                                $scope.actionlist.push(val.keyAction);
                            }
                        }
                    }

                });
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                })
            });
        };
        $scope.getList();

        // Operation List(JSON)
        $scope.operationOnPkt = function (val) {
            $scope.tableFieldList = [];
            if (val.operation != "") {
                $scope.objectdata = JSON.parse(val.operation);
                angular.forEach($scope.objectdata, function (data, key) {
                    $scope.tableFieldList.push({
                        columnName: data.columnName,
                        keySubscription: data.keySubscription,
                        namep: data.columnPosition,
                        input: data.inputType,
                        namea: data.keyAction,
                        fieldName: data.keyName,
                        placeHolder: data.keyPlaceHolder,
                        priority: data.keyPriority,
                        namero: data.keyReference,
                        namev: data.keyValidation,
                        keyValue: data.tableName,
                        roundOff: data.roundOff,
                        status: data.status,
                        sync: data.sync,
                        pktpermission: data.permission,
                        nameo: data.operator,
                        operation:data.operation,
                    });
                });
                angular.forEach($scope.tableFieldList, function (value, key) {
                    $http.post($scope.hiposServerURL + "/getPktPermisssionList?subscription=" + value.keySubscription).then(function (response) {
                        var list = response.data.object;
                        $http.post('/pkt' + '/getTableCodeLists?keyValue=' + value.keyValue + '&keySubscription=' + value.keySubscription).then(function (response) {
                            var keyCodeList = response.data.object;
                            value.tableNameListOnPkts = list;
                            value.tableFieldNameLists = keyCodeList;
                        });
                    })
                })
            }
            $scope.pktPermissionId = val.pktPermissionId;
            $scope.inputType = val.inputType;
            $scope.keySubscription = val.keySubscription;
            // $scope.tableFieldList=[];
            // $http.post("/pkt/getTableCodeListOperations?keyValue=" + $scope.keyValue + '&keySubscription=' + $scope.keySubscription).then(function (response) {
            //     var data = response.data.object;
            //     $scope.tableFieldNameListInOperation = data;
            //
            // });
            $('#cust-notif').text("");
            $("#add_new_pkt_Table_modalq").modal('show');

        };

        $scope.serialize = function () {
            $scope.result = [];
            //ONLY KEY AND VALUE
            // angular.forEach($scope.tableFieldList,function (val,key) {
            //     $scope.resultMap={};
            //     $scope.resultMap[val.fieldName]=val.columnName;
            //     $scope.result.push($scope.resultMap);
            // })

            //ONLY VALUE ,VALUE AND KEY
            angular.forEach($scope.tableFieldList, function (val, key) {
                $scope.val = {};
                if (angular.isUndefined(val.fieldName) || val.fieldName === null){
                    val.fieldName = "";
                }
                $scope.keyName1 = val.fieldName;
                if (angular.isUndefined(val.columnName) || val.columnName === null){
                    val.columnName = "";
                }
                $scope.keyValue1 = val.columnName;
                if (angular.isUndefined(val.operation) || val.operation === null){
                    val.operation = "";
                }
                $scope.operation = val.operation;
                if (angular.isUndefined(val.pktpermission) || val.pktpermission === null){
                    val.pktpermission = "";
                }
                $scope.permission = val.pktpermission;
                if (angular.isUndefined(val.keySubscription) || val.keySubscription === null){
                    val.keySubscription = "";
                }
                $scope.keySubscription1 = val.keySubscription;
                if (angular.isUndefined(val.nameo) || val.nameo === null){
                    val.nameo = "";
                }
                $scope.nameo1 = val.nameo;
                if (angular.isUndefined(val.namea) || val.namea === null){
                    val.namea = "";
                }
                $scope.namea1 = val.namea;
                if (angular.isUndefined(val.keyValue) || val.keyValue === null){
                    val.keyValue = "";
                }
                $scope.keyValue11 = val.keyValue;

                if (angular.isUndefined(val.placeHolder) || val.placeHolder === null){
                    val.placeHolder = "";
                }
                $scope.placeHolder1 = val.placeHolder;

                if (angular.isUndefined(val.namep) || val.namep === null){
                    val.namep = "";
                }
                if (angular.isUndefined(val.namev) || val.namev === null){
                    val.namev = "";
                }  if (angular.isUndefined(val.input) || val.input === null){
                    val.input = "";
                }  if (angular.isUndefined(val.priority) || val.priority === null){
                    val.priority = "";
                }  if (angular.isUndefined(val.sync) || val.sync === null){
                    val.sync = "";
                }  if (angular.isUndefined(val.fieldName) || val.fieldName === null){
                    val.fieldName = "";
                } if (angular.isUndefined(val.status) || val.status === null){
                    val.status = "";
                }if (angular.isUndefined(val.namero) || val.namero === null){
                    val.namero = "";
                }if (angular.isUndefined(val.keyVisibilitya) || val.keyVisibilitya === null){
                    val.keyVisibilitya = "";
                }
                $scope.namep1=val.namep;
                $scope.namev1 = val.namev;
                $scope.input1 = val.input;
                $scope.priority1 = val.priority;
                $scope.sync1 = val.sync;
                $scope.roundOff1 = val.fieldName;
                $scope.status1 = val.status;
                $scope.namero = val.namero;
                $scope.keyVisibilitya = val.keyVisibilitya;
                $scope.result.push({
                    keyName: $scope.keyName1,
                    keyValue: $scope.keyValue1,
                    operation: $scope.operation,
                    permission: $scope.permission,
                    keySubscription:$scope.keySubscription1,
                    operator:$scope.nameo1,
                    keyAction :$scope.namea1 ,
                    keyPlaceHolder:$scope.placeHolder1,
                    columnPosition: $scope.namep1 ,
                    keyValidation:$scope.namev1,
                    inputType:$scope.input1 ,
                    keyPriority:$scope.priority1 ,
                    sync:$scope.sync1 ,
                    roundOff:$scope.roundOff1 ,
                    status:$scope.status1,
                    tableName:$scope.keyValue11,
                    keyGroup: $scope.keyName,
                    keyVisibility: $scope.keyVisibilitya,
                    descriptor: $scope.descriptor,
                    columnName: $scope.keyValue1,
                    keyReference:$scope.namero,

                });
            })
            $scope.operations = (angular.toJson($scope.result));
            var JsonData;
            JsonData = {
                pktPermissionId: $scope.pktPermissionId,
                operation: $scope.operations
            };
            $http.post("/pkt/saveOperationJsonDatas", JsonData).then(function (response) {
                var data = response.data;
                if (data.message == "success") {
                    $scope.getpaginatedFieldList();
                    Notification.success({
                        message: 'Saved  successfully',
                        positionX: 'center',
                        delay: 2000
                    });
                }

            });
            $("#add_new_pkt_Table_modalq").modal('hide');
        }
        $scope.removefunc = function () {
            var newDataList = [];
            $scope.selectedAll = false;
            angular.forEach($scope.tableFieldList, function (selected) {
                if (!selected.selected) {
                    newDataList.push(selected);
                }
            });
            $scope.tableFieldList = newDataList;
        };
        $scope.tableFieldList = [];
        $scope.addNew = function () {
            if ($scope.tableFieldNameList == "") {
                $scope.tableFieldNameList = [];
            }
            $scope.tableFieldList.push({
                fieldName: '',
                columnName: '',
                operation: ''


            });
            $scope.numberList = [];
            $scope.numberList.push(0);
            for (i = 5; i <= 100; i += 4) {
                $scope.numberList.push(i++);
            }

        };
        //


        $scope.getOperationList = function (index, Operation) {
            $scope.tableFieldLista = [];
            $scope.fieldName1=Operation.operation;
            $scope.keyValue = Operation.keyValue;
            $scope.tableFieldLista.push({
                fieldName1: $scope.fieldName1,
                fieldName11 :$scope.keyValue
            });
            $scope.index = index;
            $('#cust-notif').text("");
            $("#add_new_pkt_Table_modal_Operation").modal('show');
        }


        $scope.serializeOperation = function () {
            $scope.result = [];
            //ONLY KEY AND VALUE
            // angular.forEach($scope.tableFieldList,function (val,key) {
            //     $scope.resultMap={};
            //     $scope.resultMap[val.fieldName]=val.columnName;
            //     $scope.result.push($scope.resultMap);
            // })

            //ONLY VALUE ,VALUE AND KEY
            angular.forEach($scope.tableFieldLista, function (val, key) {
                $scope.val = {};
                // $scope.opr = val.fieldName1 + val.status + val.fieldName2 + 'equal' + val.fieldName3;
                $scope.opr = val.fieldName1;
                $scope.result.push({
                    keyName: val.fieldName1,
                });
            });
            // $scope.opr = (angular.toJson($scope.result));
            angular.forEach($scope.tableFieldList, function (val, key) {
                if ($scope.index == key) {
                    val.operation = $scope.opr;
                }
            })
            $("#add_new_pkt_Table_modal_Operation").modal('hide');
        }


        $scope.removefuncOperation = function () {
            var newDataList = [];
            $scope.selectedAll = false;
            angular.forEach($scope.tableFieldLista, function (selected) {
                if (!selected.selected) {
                    newDataList.push(selected);
                }
            });
            $scope.tableFieldLista = newDataList;
        };
        $scope.tableFieldLista = [];
        ///OPERATION LIST(ARTHMATIC LIST)
        $scope.tableFieldLista = [];
        $scope.addNewOperation = function () {

            if ($scope.tableFieldLista == "") {
                $scope.tableFieldLista = [];
            }
            $scope.tableFieldLista.push({
                fieldName11: $scope.keyValue
            });
        };

      $scope.getoperand1list =function(){
          $http.post("/pkt/getPktFieldspermissionMasters?tableName=" + $scope.tablecode).then(function (response) {
              var data = response.data.object;
              $scope.operand1list = data;
          }, function (error) {
              Notification.error({
                  message: 'Something went wrong, please try again',
                  positionX: 'center',
                  delay: 2000
              });
          });
      }

        $scope.getoperand2list =function(){
            $http.post("/pkt/getPktFieldspermissionsMasters?tableName1=" + $scope.tablecode).then(function (response) {
                var data = response.data.object;
                $scope.operand2list = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }

        $scope.addNewPkt = function () {
            $scope.getList();
            $scope.status = "Active";
            $scope.pktPermissionId = null;
            $scope.operation = null;
            $scope.keySubscription = null;
            $scope.keyName = "";
            $scope.keyValue = null;
            $scope.keyReferenceText = "";
            $scope.keyVisibilityText = "";
            $scope.action = "";
            $scope.fieldName = null;
            $scope.placeHolder = "";
            $scope.validation = "";
            $scope.priority = "";
            $scope.sync = "Yes";
            $scope.descriptor = "";
            $scope.pktpermission = "";
            $scope.column = "";
            $scope.value = "";
            $scope.AddOpe = false;
            $scope.saveOpe = false;
            $scope.EditOpe = false;
            $scope.ListOpe = false;
            $scope.roundOff = "";
            $scope.columnName = "";
            $scope.nameo = null;
            $scope.operation = "";
            // $scope.input = null;
            // $scope.namev = null;
            $scope.namea = null;
            // $scope.namep = null;
            $scope.inputypes = null;
            $scope.keyReference = null;
            $scope.keyVisibility = "";
            $('#cust-notif').text("Add Table Column Mapping");
            $("#add_new_pkt_Table_modal").modal('show');

        };

        $scope.saveTable = function () {
            if (angular.isUndefined($scope.keySubscription) || $scope.keySubscription == '') {
                Notification.warning({
                    message: 'SubscriptionName  can not be empty',
                    positionX: 'center',
                    delay: 2000
                });
            }
            else if (angular.isUndefined($scope.keyValue) || $scope.keyValue == '') {
                Notification.warning({
                    message: 'Table Name  can not be empty',
                    positionX: 'center',
                    delay: 2000


                });
            }
            else if( (angular.isUndefined($scope.saveOpe) || $scope.saveOpe == '') && (angular.isUndefined($scope.EditOpe) || $scope.EditOpe == '')&&(angular.isUndefined($scope.ListOpe) || $scope.ListOpe == '')&&(angular.isUndefined($scope.AddOpe) || $scope.AddOpe == '')) {
                Notification.warning({
                    message: 'Please Select One Operator',
                    positionX: 'center',
                    delay: 2000
                });
            }
            // else if (angular.isUndefined($scope.nameo) || $scope.nameo == '') {
            //     Notification.warning({
            //         message: 'Operator  can not be empty',
            //         positionX: 'center',
            //         delay: 2000
            //
            //
            //     });
            // }
            // else if (angular.isUndefined($scope.namea) || $scope.namea == '') {
            //     Notification.warning({
            //         message: 'Action  can not be empty',
            //         positionX: 'center',
            //         delay: 2000
            //
            //
            //     });
            // }
            else if (angular.isUndefined($scope.fieldName) || $scope.fieldName == "" ||$scope.fieldName == null ) {
                Notification.warning({
                    message: 'Column Code  can not be empty',
                    positionX: 'center',
                    delay: 2000
                });
            }
            else {
                $scope.isDisabled = true;

                var savetable;
                savetable = {
                    pktPermissionId: $scope.pktPermissionId,
                    keySubscription: $scope.keySubscription,
                    keyName: $scope.fieldName,
                    keyValue: $scope.value,
                    keyGroup: $scope.keyName,
                    keyVisibility: $scope.keyVisibility,
                    permission: $scope.pktpermission,
                    keyValidation: $scope.namev,
                    descriptor: $scope.descriptor,
                    keyPlaceHolder: $scope.placeHolder,
                    keyAction: $scope.namea,
                    keyPriority: $scope.priority,
                    roundOff: $scope.roundOff,
                    sync: $scope.sync,
                    columnPosition: $scope.namep,
                    operator: $scope.nameo,
                    valueee: "{\"Add\":"+$scope.AddOpe+",\"Edit\":"+$scope.EditOpe+",\"List\":"+$scope.ListOpe+",\"Save\":"+$scope.saveOpe+"}",
                    columnName: $scope.columnName,
                    operation: $scope.operation,
                    tableName: $scope.keyValue,
                    inputType: $scope.inputypes,
                    keyValue: $scope.columnName,
                    status: $scope.status,
                    keyReference:$scope.keyReference
                };
                $http.post("/pkt/savePktFieldsPermsissions", savetable).then(function (response) {
                    var message = response.data;
                    if (message == "") {
                        Notification.error({message: 'Column Code exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removetableType();
                        $scope.getpaginatedFieldList();
                        $("#add_new_pkt_Table_modal").modal('hide');
                        Notification.success({
                            message: 'Table Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });

            }
        };

        $scope.getpaginatedFieldList = function (type, page) {
            if (angular.isUndefined(type)) {
                type = "Active";
            }
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.isPrev = false;
                    $scope.isNext = false;
                    $scope.pageNo = 0;
                    break;
                case 'nextPage':
                    $scope.isNext = true;
                    $scope.isPrev = false;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.pageNo = $scope.pageNo + 1;
                    break;
                case 'prevPage':
                    $scope.isPrev = true;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.pageNo = $scope.pageNo - 1;
                    break;
                default:
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isPrev = false;
                    $scope.isNext = false;
                    $scope.pageNo = 0;
            }
            var paginationDetails;
            paginationDetails = {
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if (angular.isUndefined($scope.searchText)) {
                $scope.searchText = "";
            }
            if (angular.isUndefined($scope.tablesearchText)) {
                $scope.tablesearchText = "";
            }
            if (angular.isUndefined($scope.keysearchText)) {
                $scope.keysearchText = "";
            }
            if (angular.isUndefined($scope.columnsearchText)) {
                $scope.columnsearchText = "";
            }
            if (angular.isUndefined($scope.operatorsearchText)) {
                $scope.operatorsearchText = "";
            }
            $http.post($scope.hiposServerURL + '/getPaginatedFieldLists?type=' + $scope.inactiveStatus + '&searchText=' + $scope.searchText + ' &tablesearchText=' + $scope.tablesearchText
                + '&keysearchText=' + $scope.keysearchText + '&columnsearchText=' + $scope.columnsearchText + '&operatorsearchText=' + $scope.operatorsearchText, angular.toJson(paginationDetails)).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    $scope.tabletype = data.list;
                    $scope.first = data.firstPage;
                    $scope.last = data.lastPage;
                    $scope.prev = data.prevPage;
                    $scope.next = data.nextPage;
                    $scope.pageNo = data.pageNo;
                    $scope.listStatus = true;
                    if (data == "") {
                        Notification.error({message: 'Search Not Found', positionX: 'center', delay: 2000});
                    }
                },
                function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
        };
        $scope.getpaginatedFieldList();

        $scope.dropdownbutton = function (type) {
            $scope.tablecode=type.keyGroup;
            $scope.pktPermissionId=type.pktPermissionId;
            $scope.keySubscription = type.keySubscription;
            $http.post("/pkt/editDropdown?id="+$scope.pktPermissionId).then(function (response) {
                // $scope.dropdownlist = [];
                var data = response.data;
                $scope.dropdownlist = data;
                angular.forEach($scope.dropdownlist,function (val,key) {
                    $scope.gettocolumnlist();
                    $scope.getfromcolumnlist(val.table);
                })
            })
            $scope.getTableList();

            $("#add_new_pkt_dropdown_modal").modal('show');
        }


        $scope.dropdownlist = [];
        $scope.addFooterValueOperation = function () {
            if ($scope.dropdownlist == "") {
                $scope.dropdownlist = [];
            }
            $scope.dropdownlist.push({});
        };


        $scope.calculationbutton = function(type){
            $scope.tablecode=type.keyGroup;
            $scope.pktPermissionId=type.pktPermissionId;
            $scope.calculationlist = [];
            $http.post("/pkt/editCalcualtion?id="+$scope.pktPermissionId).then(function (response) {
                var data = response.data;
                $scope.calculationlist1 = data;
                angular.forEach($scope.calculationlist1,function (val,key) {
                    var array = val.split(' ');
                    console.log(array);
                    var calculation={};
                    calculation.operand1 = array[0];
                    calculation.operation = array[1];
                    calculation.operand2 = array[2];
                    calculation.result = array[4];
                    $scope.calculationlist.push(calculation);
                });
            });
            $scope.getoperand1list();
            $("#add_new_pkt_calculation_modal").modal('show');
        };

        $scope.conditionalList=[];
        $scope.addnewCondition = function(data){
            $scope.index = data;
            $scope.conditionalList.push({})
        };

        $scope.Calculation= function(index){
            $scope.index = index;
            $scope.calculationlist=[];
                angular.forEach($scope.conditionalList, function (val, key) {
                    if(key == $scope.index){
                    if(val.result!=null) {
                        var String = angular.fromJson(val.result);
                        angular.forEach(String,function (value,key) {
                            var array = value.split(' ');
                            console.log(array);
                            var calculation = {};
                            calculation.operand1 = array[0];
                            calculation.operation = array[1];
                            calculation.operand2 = array[2];
                            calculation.result = array[4];
                            $scope.calculationlist.push(calculation);
                        })
                    }
                    }
                });
            $("#add_new_pkt_calculation1_modal").modal('show');
        };

        $scope.conditionalcalculation = function(data){
            $scope.tablecode = data.keyGroup;
            $scope.pktPermissionId = data.pktPermissionId;
            $scope.conditionalList = [];
            $http.post("/pkt/editCondition?id="+$scope.pktPermissionId).then(function (response) {
                var object = response.data;
                $scope.conditionlist1 = object;
                angular.forEach($scope.conditionlist1,function (val,key) {
                    var array = val.Condition.split(' ');
                    console.log(array);
                    var condition={};
                    condition.operand1 = array[0];
                    condition.operation = array[1];
                    condition.operand2 = array[2];
                    condition.result = val.Operation;
                    $scope.conditionalList.push(condition);
                });
                $scope.getoperand1list();
                $("#add_new_pkt_conditional_calculation_modal").modal('show');
            });
        };



        $scope.savecalculation1 = function(){

            $scope.childList=[];
            angular.forEach($scope.calculationlist,function (val,key) {
                var string = val.operand1+" "+  val.operation +" "+  val.operand2 +" = "+  val.result;
                $scope.childList.push(string);
            });
            angular.forEach($scope.conditionalList,function (val,key) {
                if($scope.index == key){
                    val.result = angular.toJson($scope.childList);
                }
            });
            $("#add_new_pkt_calculation1_modal").modal('hide');
        };


        $scope.saveCondition = function () {

            $scope.parentList=[];
            angular.forEach($scope.conditionalList,function (val,key) {
               var String = val.operand1 + " "+  val.operation+" "+  val.operand2;
               $scope.parentList.push({
                   "Condition":String,
                   "Operation":val.result
               })
            });
            $http.post("/pkt/saveCondition?id="+$scope.pktPermissionId,angular.toJson($scope.parentList)).then(function (response) {
                var data = response.data;
                $("#add_new_pkt_conditional_calculation_modal").modal('hide');
                Notification.success({
                    message: 'Condition Calculation Saved  Successfully',
                    positionX: 'center',
                    delay: 2000
                });
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            });
        };


        $scope.removeAddNewCondition = function(){
            $scope.conditionalList.pop({});
        }

        $scope.removeAddNewCalculation1 = function(){
            $scope.calculationlist.pop({});
        }
   $scope.removeAddNewCalculation = function(){
            $scope.calculationlist.pop({});
        }
        $scope.removeAddNewDropdown = function(){
            $scope.dropdownlist.pop({});
        }


        $scope.calculationlist = [];
        $scope.addnewcalculation = function(){
            if ($scope.calculationlist == "") {
                $scope.calculationlist = [];
            }
            $scope.calculationlist.push({});
        };

        $scope.createtables1list = [];
        $scope.addalltables = function(){
            if ($scope.createtables1list == "") {
                $scope.createtables1list = [];
            }
            $scope.createtables1list.push({});
        };

        $scope.getTableList = function () {
            $http.post('/pkt' + '/getTablesListBasedOnApplication?name='+$scope.keySubscription).then(function (response) {
                var data = response.data.object;
                $scope.tableslist = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                })
            });
        };

        $scope.savedropdown = function(data){

            $http.post("/pkt/saveDropdown?id="+$scope.pktPermissionId,angular.toJson(data)).then(function (response) {
                var data = response.data.object;
                $scope.codes = data;
                Notification.success({
                    message: 'Dropdown Saved  Successfully',
                    positionX: 'center',
                    delay: 2000
                });
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
            $("#add_new_pkt_dropdown_modal").modal('hide');
        };
        $scope.savecalculation = function(data){
            $scope.totalcal = data;
            $scope.resultlist = [];
            angular.forEach($scope.totalcal,function (val,key) {
                var string = val.operand1+" "+  val.operation +" "+  val.operand2 +" = "+  val.result;
                $scope.resultlist.push(string);
            $http.post("/pkt/saveCalculation?id="+$scope.pktPermissionId,angular.toJson($scope.resultlist)).then(function (response) {
                var data = response.data.object;
                $scope.codes = data;
                Notification.success({
                    message: 'Calculation Saved  Successfully',
                    positionX: 'center',
                    delay: 2000
                });
            },function (error) {
                Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
            })
            })
            $("#add_new_pkt_calculation_modal").modal('hide');
        }

        $scope.Logicalcalculationbutton = function(type){
            $scope.tablecode=type.keyGroup;
            $scope.pktPermissionId=type.pktPermissionId;
            $scope.calculationlist1 = [];
            $http.post("/pkt/editCalcualtion?id="+$scope.pktPermissionId).then(function (response) {
                var data = response.data;
                $scope.calculationlist1 = data;
                angular.forEach($scope.calculationlist1,function (val,key) {
                    var array = val.split(' ');
                    console.log(array);
                    var calculation={};
                    calculation.operand1 = array[0];
                    calculation.operation = array[1];
                    calculation.operand2 = array[2];

                    calculation.result = array[4];
                    $scope.calculationlist1.push(calculation);
                });
            });
            $scope.getoperand1list();

            $("#add_new_Logical_calculation_modal").modal('show');
        };

        $scope.calculationlist1=[];
        $scope.addparenttable = function(){
            $scope.calculationlist1.push({})
        };

        // $scope.addall1tables = function(){
        //
        //     $("#add_new_Logical_calculation1_modal").modal('show');
        // };
        });