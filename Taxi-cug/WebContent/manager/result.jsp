<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page  import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="百度地图,百度地图API，百度地图自定义工具，百度地图所见即所得工具" />
<meta name="description" content="百度地图API自定义地图，帮助用户在可视化操作下生成百度地图" />
<title>百度地图API自定义地图</title>
<!--引用百度地图API-->
<style type="text/css">
    html,body{margin:0;padding:0;}
    .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
    .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.2&services=true"></script>
</head>

<body>
  <!--百度地图容器-->
  
 <div style="border:#ccc solid 1px;" id="dituContent">
 	 <%-- <c:if test="${result_output !=null }">
 		${admin.toString() } 
 	</c:if> --%>
 </div>

</body>

<script type="text/javascript"> 
        var winWidth = 0;  
        var winHeight = 0;  
        function findDimensions() { //函数：获取尺寸  
            //获取窗口宽度  
            if (window.innerWidth) {  
                winWidth = window.innerWidth;  
            }  
            else if ((document.body) && (document.body.clientWidth)) {  
                winWidth = document.body.clientWidth;  
            }  
            //获取窗口高度  
            if (window.innerHeight) {  
                winHeight = window.innerHeight;  
            }  
            else if ((document.body) && (document.body.clientHeight)) {  
                winHeight = document.body.clientHeight;  
            }  
            //通过深入Document内部对body进行检测，获取窗口大小  
            if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {  
                winHeight = document.documentElement.clientHeight;  
                winWidth = document.documentElement.clientWidth;  
            }
            //设置div的具体宽度=窗口的宽度的%  
            if (document.getElementById("dituContent")) {  
                document.getElementById("dituContent").style.height = winHeight + "px";  
            }
            if (document.getElementById("dituContent")) {
                document.getElementById("dituContent").style.width = winWidth + "px";  
            }
        }  
        findDimensions();  
        window.onresize = findDimensions;  
</script>
<script type="text/javascript">
    //创建和初始化地图函数;
   
    function initMap(){
    	var result = <%=request.getAttribute("result_output")%>;
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
       	if(result!=null){
       //	 setLine();
       		 setPoint();
        }  
    }    
    //创建地图函数：
    function createMap(){
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(121.511282,31.243221);//定义一个中心点坐标
        
        map.centerAndZoom(point,12);//设定地图的中心点和坐标并将地图显示在地图容器中

        window.map = map;//将map变量存储在全局
    }
    function setLine(){
    	 var ret =<%=request.getAttribute("result_output")%>;
    	 var r = ret.length;
   		 var pointLine = new Array();/*  = new Array<Point>; */
    		if(ret!=null){
    			for(var i=0; i< ret.length; i++){
    				var point = new BMap.Point(Number(ret[i].longitude),Number(ret[i].altitude));
    				pointLine.push(point);
    			}
    		}
    		var ployLine = new BMap.Polyline(pointLine,{strokeColor: "blue", strokeWeight: 0.5, strokeOpacity: 0.5});
    		map.addOverlay(ployLine);
    }
    function setPoint(){
    	 var ret =<%=request.getAttribute("result_output")%>;
    //	 var r = ret.length;
   //		 var pointLine = new Array();/*  = new Array<Point>; */
    	var marker = [];  
    //	addMarker(makerPoints);//增加对应该的轨迹点  
   
    		if(ret!=null){
    			for(var i=0; i< ret.length; i++){
    	//			var result = ret[i].split(" ");
    				var point = new BMap.Point(Number(ret[i].longitude),Number(ret[i].altitude));
    	//			var hotpoint = new BMap.Hotspot(point, {text: "i love tian an men!", offsets: [100,100,100,100]/* , minZoom: 8, maxZoom: 18 */});
    				marker[i] = new BMap.Marker(point);
    		//		makerPoints.push(point);
    				map.addOverlay(marker[i]);
    		//		pointLine.push(point);
    			}
    		}
 //   		var ployLine = new BMap.Polyline(pointLine,{strokeColor: "blue", strokeWeight: 3, strokeOpacity: 0.5});
    //		map.addOverlay(ployLine);
  //  	Hotspot
    }
    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }
    
    //地图控件添加函数：
    function addMapControl(){
        //向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
    }
    initMap(); //创建和初始化地图
   
</script>
</html>