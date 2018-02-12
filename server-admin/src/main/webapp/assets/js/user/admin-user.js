var userModule = angular.module("userModule", ["ngRoute"]);

userModule.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider
			.when('/admin-user', {
				templateUrl: 'views/admin-user.html'
			})
			.when('/admin-goods', {
				templateUrl: 'views/admin-goodslist.html',
				controller:'goodslistController'

			})
			.when('/admin-feedback', {
				templateUrl: 'views/admin-feedbacklist.html'
			})
			.when('/admin-feedbacks/:id', {
				templateUrl: 'views/admin-feedback.html',
				controller: 'feedbackController'
			})
			.when('/admin-gooddetail/:id', {
				templateUrl: 'views/good-detail.html',
				controller: 'gooddetailController'
			})
			.when('/admin-editgood/:id', {
				templateUrl: 'views/good-add.html',
				controller: 'editgoodController'
			})
			.when('/admin-addgood', {
				templateUrl: 'views/good-add.html',
				controller: 'addgoodController'
			})
			.when('/admin-activitylist', {
            				templateUrl: 'views/admin-activitylist.html',
            				controller: 'activitylistController'
            })
            .when('/admin-hotgoodslist',{
                templateUrl: 'views/admin-hotgoodslist.html',
                controller: 'hotgoodslistController'
            })
			.otherwise({
				redirectTo: '/admin-user'
			});
	}
]);
userModule.factory('feedback', [

	function() {
		var feedback = {
			page: 1,
			hasNextPage: true,
			hasPrePage: false,
			feedbacks: [{
				feedback_id: 1,
				content: "",
				contact: "",
				imgs: [
					"", ""
				],
				status: ""

			}]

		};
		return feedback;
	}
]);
userModule.factory('PostIP',[function(){
  var postIP = {
    getfeedbacklist:"http://localhost:8000/feedback/list",
    updatefeedbackStatus:"http://localhost:8000/feedback/status/update",
    getgoodslist:"http://localhost:8000/item/list",
    deletegood:"http://localhost:8000/item/delete",
    searchgood:"http://localhost:8000/item/search",
    getgooddetail:"http://localhost:8000/item/get",
    getdetail:"http://localhost:8000/item/render",
    uploadimg:"http://localhost:8000/upload/img",
    uploadimgs:"http://localhost:8000/upload/imgs",
    addgood:"http://localhost:8000/item/add",
    addactivity:"http://localhost:8000/activity/add",
    getactivitylist:"http://localhost:8000/activity/list",
    deleteactivity:"#",
    hotgoodslist:"http://localhost:8000/item/hot/list",
    sethotgood:"http://localhost:8000/item/hot/add",
    deletehotgood:"http://localhost:8000/item/hot/delete",
    gethotgoodId:"http://localhost:8000/item/hot/ids/list",
    delivery:"http://localhost:8000/acitivity/delivery"
  };
  return postIP;
}]);
userModule.controller('feedbacklistController', ["$scope", "feedback", "$http","PostIP",
	function($scope, feedback, $http,PostIP) {
	    var progress = $.AMUI.progress;
	     $scope.page=1;
		$scope.getfeedbacklist = function() {
		    progress.start();
			$.post(PostIP.getfeedbacklist, "page=" + $scope.page,function(data) {
                $scope.$apply(function(){

				feedback.page = data.currentPage;
				feedback.feedbacks = data.content;
				feedback.hasNextPage = data.hasNextPage;
				feedback.hasPrePage = data.hasPrePage;
				$scope.feedbacks = data.content;
				$scope.feedbacks.forEach(function(item) {
					switch (item.status) {
						case 0:
							item.status = "未处理";
							break;
						case 1:
							item.status = "处理中";
							break;
						case 2:
							item.status = "处理完成";
							break;
						case 3:
							item.status = "忽略";
							break;

					};
				});
				$scope.page = data.currentPage;
				$scope.hasNextPage = data.hasNextPage;
				$scope.hasPrePage = data.hasPrePage;
				if (!$scope.hasNextPage) {
                        					$("#nextpage").addClass('am-disabled');
                        				}
                        				else{
                        				    $("#nextpage").removeClass('am-disabled');
                        				}
                        				if (!$scope.hasPrePage) {
                        					$("#prepage").addClass('am-disabled');
                        				}
                        				else{
                                           $("#prepage").removeClass('am-disabled');
                                        }
				progress.done();
				 });
			});
		}
		$scope.getNextPage = function() {
			if ($scope.hasNextPage) {
				$scope.page++;
				$scope.getfeedbacklist();
			} else {
				return false;
			}
		}

		$scope.getPrePage = function() {
			if ($scope.hasPrePage) {
				$scope.page--;
				$scope.getfeedbacklist();
			} else {
				return false;
			}
		}
		$scope.getfeedbacklist();
	}
]);
userModule.controller('feedbackController', ["$scope", "feedback", "$http", "$routeParams","PostIP",
	function($scope, feedback,$http, $routeParams ,PostIP) {
		$scope.id = $routeParams.id;
		var feedbacklist = feedback.feedbacks;
		var originStatus;
		feedbacklist.forEach(function(item) {
			if (item.feedback_id == $scope.id) {
				$scope.feedback = item;
				if(item.status=="未处理"){
				  originStatus=0;}
				else if(item.status=="处理中"){
				  originStatus=1;
				}
				else if(item.status=="处理完成"){
                				  originStatus=2;
                				}
                else if(item.status=="忽略"){
                                				  originStatus=3;
                                				}
				return;
			}
		});
		$scope.updateStatus = function() {
             $.post(PostIP.updatefeedbackStatus,"feedback_id=" + $scope.id+"&status="+ $scope.status,function(data){
             if (data.result == "success") {
             					alert(data.message);
             				}
             				if (data.result == "failed") {
             					alert(data.message);
             				}
             });
//			$http.post(PostIP.updatefeedbackStatus, "feedback_id=" + $scope.id+"&status=" + $scope.status).success(function(data) {
//				if (data.result == "success") {
//					alert(data.message);
//				}
//				if (data.result == "failed") {
//					alert(data.message);
//				}
//			}).error(function(data, status, headers, config){
//              			  alert("错误："+status);
//              			});
		};

		$scope.$watch('status', function(){
            if($scope.status)
		       {
		         if($scope.status!=originStatus){
		            $scope.updateStatus();
		          }
		       }
                });
	}
]);
userModule.controller('goodslistController', ["$scope", "$http","PostIP",
	function($scope, $http,PostIP) {
	var progress = $.AMUI.progress;
	$scope.page=1;
	$scope.banner_url=[];
	$scope.init = function(){
	var oBtn = document.getElementById("bannerupload");
	$(document).on('mouseenter', '.imagegallery', function() {
            			$(this).find('.delete_img').removeClass('hide');
            		});
            		$(document).on('mouseleave', '.imagegallery', function() {
            			$(this).find('.delete_img').addClass('hide');
            		});
    	 new AjaxUpload(oBtn, {
            				action: PostIP.uploadimgs,
            				name: "files",
            				onSubmit: function(file, ext) {
            					if (ext && /^(jpg|jpeg|png|gif)$/.test(ext)) {
            						oBtn.value = "正在上传…";
            						oBtn.disabled = "disabled";
            					} else {
            						oBtn.value = "不支持非图片格式！";
            						return false;
            					}
            				},
            				onComplete: function(file, data) {
            					oBtn.disabled = "";
            					oBtn.value = "图片上传";
            					var start = data.indexOf(">");
            					if (start != -1) {
                                     var end = data.indexOf("<", start + 1);
                                     if (end != -1) {
                                      data = data.substring(start + 1, end);
                                         }
                                }
                                     data = eval('(' + data + ')');
            					$scope.$apply(function () {
            					$scope.banner_url=$scope.banner_url.concat(data);
            					});

            				}
            			});
	};
	$scope.getgoodslist = function() {
	progress.start();
			$.post(PostIP.getgoodslist, "page=" + $scope.page,function(data) {
                $scope.$apply(function(){

				$scope.goods = data.content;

				$scope.page = data.currentPage;
				$scope.hasNextPage = data.hasNextPage;
				$scope.hasPrePage = data.hasPrePage;
				$scope.setType();
				if (!$scope.hasNextPage) {
                        					$("#nextpage").addClass('am-disabled');
                        				}
                        				else{
                        				    $("#nextpage").removeClass('am-disabled');
                        				}
                        				if (!$scope.hasPrePage) {
                        					$("#prepage").addClass('am-disabled');
                        				}
                        				else{
                                           $("#prepage").removeClass('am-disabled');
                                        }
				progress.done();
				});
			});

		};

	$scope.setType = function(){

	    $http.post(PostIP.gethotgoodId).success(function(data){
	       var hotgoodId =data;
	       $scope.goods.forEach(function(item,index,array){
                               	      if(hotgoodId.indexOf(parseInt(item.item_id))!=-1){

                               	            item.type = "热销商品";
                               	        }
                               	      else{
                               	            item.type = "普通商品";
                               	        }
                               	    });
	    });


	};
	$scope.getNextPage = function() {
			if ($scope.hasNextPage) {
				$scope.page++;
				$scope.getgoodslist();
			} else {
				return false;
			}
		};
	$scope.getPrePage = function() {
			if ($scope.hasPrePage) {
				$scope.page--;
				$scope.getgoodslist();
			} else {
				return false;
			}
		};
	$scope.deletegood = function(id) {
			$http.post(PostIP.deletegood, 'item_id=' + id).success(function(data) {
				if (data.result == "success") {
					$scope.goods.forEach(function(item, index, array) {
						if (item.item_id == id) {
							array.splice(index, 1);
							return;
						}
					});
				}
				if (data.result == "failed") {
					alert(data.message);
				}
			}).error(function(data, status, headers, config){
              			  alert("错误："+status);
              			});
		};
	$scope.searchgood = function() {
		   $.post(PostIP.searchgood, 'keywords=' + $scope.keywords,function(data){
                $scope.$apply(function(){
                  $scope.goods = data;
                });
		    });
};
//		 熱銷商品部分
	$scope.sethotgood = function(id){
		    var data  ="item_id="+id+"&banner_url="+$scope.banner_url[0];
		    $.post(PostIP.sethotgood,data,function(data){
		        if(data.result=="success"){
		        alert(data.message);
		        $scope.hotgood_reset();}
		        if(data.result=="failed"){
                		        alert(data.message);}
		    });

		};
	$scope.deletebannerImg = function(url){
	 $scope.banner_url.forEach(function(item, index, array) {
                				if (url == item) {
                				    console.log($scope.banner_url);
                					array.splice(index, 1);
                					console.log($scope.banner_url);
                					return;
                				}
                			});
		};
	$scope.hotgood_reset = function(){
		        $scope.$apply(function(){
		            $scope.banner_url=[];
		        });
		};

	$scope.getgoodslist();
	}]);
userModule.controller('gooddetailController', ['$scope', '$http', '$routeParams',"PostIP",
	function($scope, $http, $routeParams,PostIP) {
		$scope.id = $routeParams.id;
		$.post(PostIP.getgooddetail, 'item_id=' + $scope.id,function(data){
		$scope.$apply(function(){
		                    $scope.name = data.base.name;
                			$scope.price = data.base.price;
                			$scope.property = data.base.property;
                			$scope.detail = data.base.detail;
                			$scope.thumbnail_url = data.base.thumbnail_url;
                			$scope.desc_img_urls = data.base.desc_img_urls;
                			$scope.activity_num = data.statistics.activity_num;
                			$scope.view_num = data.statistics.view_num;
                			$scope.reply_num = data.statistics.reply_num;
		});
		});

		$http.post(PostIP.gethotgoodId).success(function(data){
        	       var hotgoodId =data;
        	       var id = parseInt($scope.id);
        	        if(hotgoodId.indexOf(id)!=-1){
                           	            $http.post(PostIP.hotgoodslist).success(function(data){
                           	                data.forEach(function(item,index){
                           	                    if(item.item_id==$scope.id)
                           	                    {
                           	                        $scope.banner_url=item.banner_url;
                           	                        return;
                           	                    }
                           	                });
                           	            });
                           	        }

        	    });


//		$http.post(PostIP.getgooddetail, 'item_id=' + $scope.id).success(function(data) {
//			$scope.name = data.base.name;
//			$scope.price = data.base.price;
//			$scope.property = data.base.property;
//			$scope.detail = data.base.detail;
//			$scope.thumbnail_url = data.base.thumbnail_url;
//			$scope.desc_img_urls = data.base.desc_img_urls;
//			$scope.activity_num = data.statistics.activity_num;
//			$scope.view_num = data.statistics.view_num;
//			$scope.reply_num = data.statistics.reply_num;
//		}).error(function(data, status, headers, config){
//          			  alert("错误："+status);
//          			});
		$scope.getdetail = function() {
					window.open(PostIP.getdetail+"?"+"item_id="+$scope.id, '_blank', 'height=667,width=375,location=no');

		};
		$scope.activity_reset = function(){
		    $scope.$apply(function(){
		         $scope.activity_price=$scope.hour=$scope.min = $scope.second = '';
		    });

        		};
		$scope.activity_upload = function(){
		     $scope.time = $scope.hour+":"+$scope.min+":"+$scope.second;
		     var data = "item_id="+$scope.id+"&activity_price="+$scope.activity_price+"&reward_interval="+$scope.time;

		     $.post(PostIP.addactivity,data,function(data){
		     if(data.result=="success"){
             		        alert("添加成功");
             		        $scope.activity_reset();}
		     });
//		     $http.post(PostIP.addactivity,data).success(function(data){
//		        if(data.result=="success"){
//		        alert("添加成功");
//		        $scope.activity_reset();}
//		     }).error(function(data, status, headers, config){
//               			  alert("错误："+status);
//               			});
		};

	}
]);
userModule.controller('addgoodController', ['$scope', '$http',"PostIP",
	function($scope, $http,PostIP) {
        $scope.thumbnail_url=[];
        $scope.desc_img_urls=[];
		$scope.init = function() {
			$('#good_tags').tagEditor(); //初始化标签
			$('#edit').editable({
				inlineMode: false,
				alwaysBlank: true,
				height: '667px',
				imageUploadURL: PostIP.uploadimg
			}); //初始化富文本编辑器
			var oBtn1 = document.getElementById("unloadPic1");
			var oBtn2 = document.getElementById("unloadPic2");
			new AjaxUpload(oBtn1, {
				action: PostIP.uploadimgs,
				name: "files",
				onSubmit: function(file, ext) {
					if (ext && /^(jpg|jpeg|png|gif)$/.test(ext)) {
						oBtn1.value = "正在上传…";
						oBtn1.disabled = "disabled";
					} else {
						oBtn1.value = "不支持非图片格式！";
						return false;
					}
				},
				onComplete: function(file, data) {
					oBtn1.disabled = "";
					oBtn1.value = "图片上传";
					var start = data.indexOf(">");
					if (start != -1) {
                         var end = data.indexOf("<", start + 1);
                         if (end != -1) {
                          data = data.substring(start + 1, end);
                             }
                    }
                             data = eval('(' + data + ')');
					$scope.$apply(function () {
					$scope.thumbnail_url=$scope.thumbnail_url.concat(data);
					});

				}
			});
			new AjaxUpload(oBtn2, {
				action: PostIP.uploadimgs,
				name: "files",
				onSubmit: function(file, ext) {
					if (ext && /^(jpg|jpeg|png|gif)$/.test(ext)) {
						oBtn2.value = "正在上传…";
						oBtn2.disabled = "disabled";
					} else {
						oBtn2.value = "不支持非图片格式！";
						return false;
					}
				},
				onComplete: function(file, data) {
					oBtn2.disabled = "";
					oBtn2.value = "图片上传";
					var start = data.indexOf(">");
                    if (start != -1) {
                        var end = data.indexOf("<", start + 1);
                         if (end != -1) {
                           data = data.substring(start + 1, end);
                         }
                         }
                         data = eval('(' + data + ')');
                         $scope.$apply(function () {
					$scope.desc_img_urls=$scope.desc_img_urls.concat(data);
					});
				}
			});

		};
		$scope.init();
		$(document).on('mouseenter', '.imagegallery', function() {
        			$(this).find('.delete_img').removeClass('hide');
        		});
        		$(document).on('mouseleave', '.imagegallery', function() {
        			$(this).find('.delete_img').addClass('hide');
        		});
		$scope.reset = function() {
			$scope.item_name = '';
			$scope.price = '';
			$('#good_tags').tagEditor('getTags')[0].tags.forEach(function(item) {
				$('#good_tags').tagEditor('removeTag', item);
			});
			$('#edit').editable('setHTML', '', false);
			$scope.thumbnail_url=[];
            $scope.desc_img_urls=[];

		};
		$scope.deleteThumbnailImg = function() {
        			$scope.thumbnail_url.splice(0, 1);
        		};
        $scope.deleteDescImg = function(url) {
        			$scope.desc_img_urls.forEach(function(item, index, array) {
        				if (url == item) {
        					array.splice(index, 1);
        					return;
        				}
        			});
        		};
		$scope.upload = function() {
			$scope.tags = $('#good_tags').tagEditor('getTags')[0].tags;
			$scope.detail = $('#edit').editable('getHTML', true, true);
			var data = {
            				"name": $scope.item_name,
            				"price": $scope.price,
            				"properties": $scope.tags,
            				"thumbnail_url": $scope.thumbnail_url[0],
            				"image_urls": $scope.desc_img_urls,
            				"detail": $scope.detail
            			};

			$http.post(PostIP.addgood, data).success(function(data) {
				if (data.result == "success") {
					alert(data.message);
					$scope.reset();
				}
				if (data.result == "failed") {
					alert(data.message);

				}
			}).error(function(data, status, headers, config){
              			  alert("错误："+status);
              			});


		};
	}
]);
userModule.controller('editgoodController', ['$scope', '$routeParams', '$http',"PostIP",
	function($scope, $routeParams, $http,PostIP) {
		$scope.id = $routeParams.id;
		$scope.init = function() {
			$('#good_tags').tagEditor(); //初始化标签
			$('#edit').editable({
				inlineMode: false,
				alwaysBlank: true,
				height: '667px',
				imageUploadURL: '#'
			}); //初始化富文本编辑器
			//初始化图片上传
			var oBtn1 = document.getElementById("unloadPic1");
            			var oBtn2 = document.getElementById("unloadPic2");
			new AjaxUpload(oBtn1, {
            				action: PostIP.uploadimgs,
            				name: "files",
            				onSubmit: function(file, ext) {
            					if (ext && /^(jpg|jpeg|png|gif)$/.test(ext)) {
            						oBtn1.value = "正在上传…";
            						oBtn1.disabled = "disabled";
            					} else {
            						oBtn1.value = "不支持非图片格式！";
            						return false;
            					}
            				},
            				onComplete: function(file, data) {
            					oBtn1.disabled = "";
            					oBtn1.value = "图片上传";
            					var start = data.indexOf(">");
            					if (start != -1) {
                                     var end = data.indexOf("<", start + 1);
                                     if (end != -1) {
                                      data = data.substring(start + 1, end);
                                         }
                                }
                                         data = eval('(' + data + ')');
            					$scope.$apply(function () {
            					$scope.thumbnail_url=$scope.thumbnail_url.concat(data);
            					});

            				}
            			});
            new AjaxUpload(oBtn2, {
            				action: PostIP.uploadimgs,
            				name: "files",
            				onSubmit: function(file, ext) {
            					if (ext && /^(jpg|jpeg|png|gif)$/.test(ext)) {
            						oBtn2.value = "正在上传…";
            						oBtn2.disabled = "disabled";
            					} else {
            						oBtn2.value = "不支持非图片格式！";
            						return false;
            					}
            				},
            				onComplete: function(file, data) {
            					oBtn2.disabled = "";
            					oBtn2.value = "图片上传";
            					var start = data.indexOf(">");
                                if (start != -1) {
                                    var end = data.indexOf("<", start + 1);
                                     if (end != -1) {
                                       data = data.substring(start + 1, end);
                                     }
                                     }
                                     data = eval('(' + data + ')');
                                     $scope.$apply(function () {
            					$scope.desc_img_urls=$scope.desc_img_urls.concat(data);
            					});
            				}
            			});


		};
        $.post(PostIP.getgooddetail, 'item_id=' + $scope.id,function(data){
                  $scope.$apply(function(){
                  $scope.item_name = data.base.name;
                  $scope.price = data.base.price;
                  $scope.property = data.base.property;
                  $scope.property.forEach(function(item) {
                      $('#good_tags').tagEditor('addTag', item);
                  });
                  $('#edit').editable('setHTML', data.base.detail, false);
                  $scope.thumbnail_url=[];
                  $scope.thumbnail_url.push(data.base.thumbnail_url);
                  $scope.desc_img_urls = data.base.desc_img_urls;
                   console.log($scope.thumbnail_url);
                   console.log($scope.desc_img_urls);
                  });

        });
//		$http.post(PostIP.getgooddetail, 'item_id=' + $scope.id).success(function(data) {
//			$scope.item_name = data.base.name;
//			$scope.price = data.base.price;
//			$scope.property = data.base.property;
//			$scope.property.forEach(function(item) {
//				$('#good_tags').tagEditor('addTag', item);
//			});
//			$('#edit').editable('setHTML', data.base.detail, false);
//			$scope.thumbnail_url = data.base.thumbnail_url;
//			$scope.desc_img_urls = data.base.desc_img_urls;
//		}).error(function(data, status, headers, config){
//          			  alert("错误："+status);
//          			});
		$scope.init();
		var orgin_name = angular.copy($scope.item_name);
		var orgin_price = angular.copy($scope.price)
		$(document).on('mouseenter', '.imagegallery', function() {
			$(this).find('.delete_img').removeClass('hide');
		});
		$(document).on('mouseleave', '.imagegallery', function() {
			$(this).find('.delete_img').addClass('hide');
		});
		$scope.reset = function() {
			$scope.item_name = angular.copy(orgin_name);
			$scope.price = angular.copy(orgin_price);
			$('#good_tags').tagEditor('getTags')[0].tags.forEach(function(item) {
				$('#good_tags').tagEditor('removeTag', item);
			});
			$('#edit').editable('setHTML', '', false);
			$scope.thumbnail_url=[];
			$scope.desc_img_urls=[];

		};
		$scope.deleteThumbnailImg = function() {
			$scope.thumbnail_url.splice(0, 1);
		};
		$scope.deleteDescImg = function(url) {
			$scope.desc_img_urls.forEach(function(item, index, array) {
				if (url == item) {
					array.splice(index, 1);
					return;
				}
			});
		};
		$scope.upload = function() {
			$scope.tags = $('#good_tags').tagEditor('getTags')[0].tags;
			$scope.detail = $('#edit').editable('getHTML', true, true);
			var data = {
				"name": $scope.item_name,
				"price": $scope.price,
				"properties": $scope.tags,
				"thumbnail_url": $scope.thumbnail_url[0],
				"image_urls": $scope.desc_img_urls,
				"detail": $scope.detail
			};
			$http.post(PostIP.addgood, data).success(function(data) {
				if (data.result == "success") {
					alert(data.message);
					$scope.reset();
				}
				if (data.result == "failed") {
					alert(data.message);

				}
			}).error(function(data, status, headers, config){
              			  alert("错误："+status);
              			});


		};
	}
]);
userModule.controller('activitylistController',['$scope','$http','PostIP',function($scope,$http,PostIP){
  var progress = $.AMUI.progress;
  $scope.page = 1;
        $scope.getactivitylist = function() {
        progress.start();
        $.post(PostIP.getactivitylist, 'page='+$scope.page,function(data) {
            $scope.$apply(function(){
        				$scope.activitys = data.content;
        				$scope.page = data.currentPage;
        				$scope.hasNextPage = data.hasNextPage;
        				$scope.hasPrePage = data.hasPrePage;
        				$scope.activitys.forEach(function(item) {
                        					switch (item.activity_status) {
                        						case 0:
                        							item.activity_status = "未开始";
                        							break;
                        						case 1:
                        							item.activity_status = "进行中";
                        							break;
                        						case 2:
                        							item.activity_status = "待开奖";
                        							break;
                        						case 3:
                        							item.activity_status = "待发货";
                        							break;
                        						case 4:
                                                    item.activity_status = "已发货";
                                                    break;

                        					};
                        				});
        				if (!$scope.hasNextPage) {
        					$("#nextpage").addClass('am-disabled');
        				}
        				else{
        				    $("#nextpage").removeClass('am-disabled');
        				}
        				if (!$scope.hasPrePage) {
        					$("#prepage").addClass('am-disabled');
        				}
        				else{
                           $("#prepage").removeClass('am-disabled');
                        }
        				progress.done();
        				});
        			});

        		};
        		$scope.getNextPage = function() {
        			if ($scope.hasNextPage) {
        				$scope.page++;
        				$scope.getactivitylist();
        			} else {
        				return false;
        			}
        		};
        		$scope.getPrePage = function() {
        			if ($scope.hasPrePage) {
        				$scope.page--;
        				$scope.getactivitylist();
        			} else {
        				return false;
        			}
        		};
        		$scope.deleteactivity = function(id) {
        			$.post(PostIP.deleteactivity, 'activity_id=' + id,function(data) {
        				if (data.result == "success") {
        					$scope.goods.forEach(function(item, index, array) {
        						if (item.item_id == id) {
        							array.splice(index, 1);
        							return;
        						}
        					});
        				}
        				if (data.result == "failed") {
        					alert(data.message);
        				}
        			});
        		};
        		$scope.delivery = function(id){
        		    $http.post(PostIP.delivery,"activity_id="+id+"&express_name="+$scope.express_name).success(function(data){
        		        if(data.result=="success"){
        		            alert(data.message);
        		        $scope.activitys.forEach(function(item){
        		                if(item.activity_id==id){
        		                    $scope.$apply(function(){
        		                        item.activity_status=="已发货";
        		                    });
        		                    return;
        		                }
        		            });
        		        }
        		        if(data.result=="failed"){
        		            alert(data.message);
        		        }
        		    });


        		};
        		$scope.delivery_reset = function(){
        		    $scope.express_name='';
        		   }
        		$scope.getactivitylist();
}]);
userModule.controller('hotgoodslistController',['$scope','$http','PostIP',function($scope,$http,PostIP){
        var progress = $.AMUI.progress;
        progress.start();
        $http.post(PostIP.hotgoodslist).success(function(data){

                $scope.hotgoods = data;
                progress.done();

        });
        $scope.deletehotgood = function(id){

          $.post(PostIP.deletehotgood, 'id=' + id,function(data) {
          				if (data.result == "success") {
          				   $scope.$apply(function(){
          				    $scope.hotgoods.forEach(function(item, index, array) {
                                      						if (item.item_id == id) {
                                      							array.splice(index, 1);
                                      							return;
                                      						}
                                      					});
          				   });

          				}
          				if (data.result == "failed") {
          					alert(data.message);
          				}
          			});

        }
}]);
//用户数据表格
require.config({
	paths: {
		echarts: 'http://echarts.baidu.com/build/dist'
	}
});
var storage = window.localStorage;

function getdayUser() {
	$.ajax({
		type: 'POST',
		url:"http://localhost:8000/data/user/get",
		data: 'last_time=' + storage.getItem("last_time_day") + '&scope=0',
		success: function(data) {
			var userdata = data;
			var chartX = [],
				increase = [];
			userdata.data.forEach(function(item, index) {
				chartX.push(item.timestamp);
				increase.push(item.increase);
			});
			if (!storage.getItem("last_time_day")) {
				storage.setItem("last_time_day", userdata.create_time);
			}
			$('#user_day').text(userdata.increased_num);
			require(
				[
					'echarts',
					'echarts/chart/line'

				],
				function(ec) {
					var myChart1 = ec.init(document.getElementById('chart'));
					option = {
						title: {
							text: '24小时用户增加量变化',
						},
						tooltip: {
							trigger: 'axis'
						},
						legend: {
							data: ['用户增加量']
						},
						toolbox: {
							show: true,
							feature: {
								mark: {
									show: true
								},
								dataView: {
									show: true,
									readOnly: false
								},
								restore: {
									show: true
								},
								saveAsImage: {
									show: true
								}
							}
						},
						calculable: true,
						xAxis: [{
							type: 'category',
							boundaryGap: false,
							data: chartX
						}],
						yAxis: [{
							type: 'value',
							axisLabel: {
								formatter: '{value} '
							}
						}],
						series: [{
							name: '用户增加量',
							type: 'line',
							data: increase,
							markPoint: {
								data: [{
									type: 'max',
									name: '最大值'
								}, {
									type: 'min',
									name: '最小值'
								}]
							},
							markLine: {
								data: [{
									type: 'average',
									name: '平均值'
								}]
							}
						}]
					};
					myChart1.setOption(option);
				});
		}
	});
};

function getweekUser() {
	$.ajax({
		type: "post",
		url: "http://localhost:8000/data/user/get",
		async: true,
		data: 'last_time=' + storage.getItem("last_time_week") + '&scope=1',
		success: function(data) {
			var userdata = data;
			var chartX = [],
				increase = [];
			userdata.data.forEach(function(item, index) {
				chartX.push(item.timestamp);
				increase.push(item.increase);
			});
			if (!storage.getItem("last_time_week")) {
				storage.setItem("last_time_week", userdata.create_time);
			}
			$('#user_week').text(userdata.increased_num);
			require(
				[
					'echarts',
					'echarts/chart/line'

				],
				function(ec) {
					var myChart2 = ec.init(document.getElementById('chart'));

					option = {
						title: {
							text: '7天用户增加量变化',
						},
						tooltip: {
							trigger: 'axis'
						},
						legend: {
							data: ['用户增加量']
						},
						toolbox: {
							show: true,
							feature: {
								mark: {
									show: true
								},
								dataView: {
									show: true,
									readOnly: false
								},
								restore: {
									show: true
								},
								saveAsImage: {
									show: true
								}
							}
						},
						calculable: true,
						xAxis: [{
							type: 'category',
							boundaryGap: false,
							data: chartX
								//						['周一', '周二', '周三', '周四', '周五', '周六', '周日']
						}],
						yAxis: [{
							type: 'value',
							axisLabel: {
								formatter: '{value} '
							}
						}],
						series: [{
							name: '用户增加量',
							type: 'line',
							data: increase,
							markPoint: {
								data: [{
									type: 'max',
									name: '最大值'
								}, {
									type: 'min',
									name: '最小值'
								}]
							},
							markLine: {
								data: [{
									type: 'average',
									name: '平均值'
								}]
							}
						}]
					};

					myChart2.setOption(option);
				});
		}
	});
};

function getmonthUser() {
	$.ajax({
		type: "post",
		url: "http://localhost:8000/data/user/get",
		async: true,
		data: 'last_time=' + storage.getItem("last_time_month") + '&scope=2',
		success: function(data, textStatus) {
			var userdata = data;
			var chartX = [],
				increase = [];
			userdata.data.forEach(function(item, index) {
				chartX.push(item.timestamp);
				increase.push(item.increase);
			});
			if (!storage.getItem("last_time_month")) {
				storage.setItem("last_time_month", userdata.create_time);
			}
			$('#user_month').text(userdata.increased_num);
			require(
				[
					'echarts',
					'echarts/chart/line'

				],
				function(ec) {
					var myChart3 = ec.init(document.getElementById('chart'));

					option = {
						title: {
							text: '30天内用户增加量变化',
						},
						tooltip: {
							trigger: 'axis'
						},
						legend: {
							data: ['用户增加量']
						},
						toolbox: {
							show: true,
							feature: {
								mark: {
									show: true
								},
								dataView: {
									show: true,
									readOnly: false
								},
								restore: {
									show: true
								},
								saveAsImage: {
									show: true
								}
							}
						},
						calculable: true,
						xAxis: [{
							type: 'category',
							boundaryGap: false,
							data: chartX
								//						['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30']
						}],
						yAxis: [{
							type: 'value',
							axisLabel: {
								formatter: '{value} '
							}
						}],
						series: [{
							name: '用户增加量',
							type: 'line',
							data: increase, //[110, 110, 150, 130, 120, 130, 100, 110, 150, 130, 120, 130, 100, 110, 150, 130, 120, 130, 100, 110, 150, 130, 120, 130, 100, 110, 150, 130, 120, 130],
							markPoint: {
								data: [{
									type: 'max',
									name: '最大值'
								}, {
									type: 'min',
									name: '最小值'
								}]
							},
							markLine: {
								data: [{
									type: 'average',
									name: '平均值'
								}]
							}
						}]
					};


					myChart3.setOption(option);
				});
		}
	});
};
$(document).ajaxError(function(event,xhr,options,exc){
  alert("错误:"+xhr.status);
});