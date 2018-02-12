var loginModule = angular.module('login',[]);
loginModule.controller("loginController",["$scope","$http",function($scope,$http){

$scope.login = function(){
	var data = $("#login_form").serialize();
	$http({method:'POST',url:'#',params:data}).success(function(response) {
	     responsedata=response; 
	     if(responsedata.result=="success"){
	     	$location.path('admin.html');
	     }
            if(responsedata.result=="failed")  
            {  
                alert(responsedata.message);  
            }  
            return true;  }).error(function(status){
            	alert("错误:"+status);
            });
}; }]);

