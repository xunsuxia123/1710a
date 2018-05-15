<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/25
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
    <jsp:include page="bootcommon.jsp" flush="true"></jsp:include>
</head>
<body>

<input type="button" href="#modal-container-508750" data-toggle='modal' class="btn btn-info" value="新增"/>
<input type="button" class="btn btn-info" value="导出word文档" onclick="importNews()"/>
<table id="tabls"></table>


<form class="form-login" id="addusera" action="index.jsp">
<div class="modal fade" id="modal-container-508750" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
                <h4 class="modal-title" id="myModalLabel">新闻新增</h4>
            </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">新闻名称</label>
                        <div class="col-sm-5">
                            <input type="text" name="newsname">
                        </div>
                    </div>
                    <br>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">新闻内容</label>
                        <div class="col-sm-5">
                            <textarea  name="newsintr"></textarea>
                        </div>
                    </div>
                </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal">关闭</button>
                <button class="btn btn-primary" type="button" onclick="addNews()">保存</button>
            </div>
        </div>
    </div>
</div>
</form>

<div class="modal fade" id="modal-container-123456" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
    <form class="form-login" id="updateuser" action="index.jsp">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
                    <h4 class="modal-title">新闻修改页面</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="newsid"/>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">新闻名称</label>
                        <div class="col-sm-5">
                            <input type="text" name="newsname">
                        </div>
                    </div>
                    <br>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">新闻内容</label>
                        <div class="col-sm-5">
                            <textarea  name="newsintr"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" type="button" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary" type="button" onclick="updateNews()">保存</button>
                </div>
            </div>

        </div>
    </form>
</div>



<script type="text/javascript">

    $(function(){
        $('#tabls').bootstrapTable({
            url:'<%=request.getContextPath()%>/news/queryNews',
            striped: true,//隔行变色
            showPaginationSwitch:true,//是否显示 数据条数选择框
            minimumCountColumns:1,//最小留下一个
            showRefresh:true,//显示刷新按钮
            showToggle:true,//显示切换视图
            search:true,//是否显示搜索框
            searchOnEnterKey:true,//设置为 true时，按回车触发搜索方法，否则自动触发搜索方
            //bootstrap默认是客户端分页client 若写服务端则出不来结果server
            sidePagination:"",//
            pagination:true,//开启分页
            paginationLoop:true,//开启分页无限循环
            pageNumber:1,//当前页数
            pageSize:3,//每页条数
            pageList:[1,3,5],//如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
            method:'post',//发送请求的方式
            contentType:"application/x-www-form-urlencoded",//必须的否则条件查询时会乱码
            onDblClickRow: function (row) {
                BootstrapDialog.show({
                    title: "新闻详情",       //title
                    message: $('<div></div>').load("<%=request.getContextPath()%>/news/queryTwoNews?newsid="+row.newsid),
                    buttons : [
                        /*{
                        label : "导出",
                        action : function(dialog){
                            $.ajax({
                                url : "<%=request.getContextPath()%>/news/importNews",
                                type : "post",
                                data : $("#WordForm").serialize(),
                                dataType:"json",
                                success : function(flag){
                                    if(flag==1){
                                        location.reload();
                                    }

                                }
                            })
                        }
                    },*/
                        {
                        label : "关闭",
                        action : function(dialog){
                            dialog.close();
                        }
                    }]
                })
            },
            columns:[[
                {field:'newsid',title:'新闻编号',width:100},
                {field:'newsname',title:'新闻标题',width:100},
                {field:'newsintr',title:'新闻内容',width:100},
                {field:'caozuo',title:'操作',width:100,
                    formatter:function(value,row,index){
                        return '<button type="button" class="btn btn-default" onclick="deleteNews('+row.newsid+')">删除</button>&nbsp;&nbsp;'+
                                '<button href="#modal-container-123456"   data-toggle="modal" type="button" class="btn btn-primary" onclick="queryByIdNews('+row.newsid+')">修改</button>';
                    }}
            ]]
        })
    })

    function deleteNews(newsid){
        $.ajax({
            url:"<%=request.getContextPath()%>/news/deleteNews",
            type:"post",
            data:{"newsid":newsid},
            dataType:"text",
            async:false,
            success:function(){
                location.reload();
            },
            error:function(){
                alert("删除出错");
            }
        })
    }
    //新增
    function addNews(){
        $.ajax({
            url:"<%=request.getContextPath()%>/news/addNews",
            type:"post",
            data:$("#addusera").serialize(),
            dataType:"text",
            async:false,
            success:function(){
                location.reload();
            },
            error:function(){
                alert("新增出错");
            }
        })
    }
    //回显
    function queryByIdNews(newsid){
        $.ajax({
            url:"<%=request.getContextPath()%>/news/queryByIdNews",
            type:"post",
            data:{"newsid":newsid},
            dataType:"json",
            async:false,
            success:function(news){
                $("[name='newsid']").val(news.newsid);
                $("[name='newsname']").val(news.newsname);
                $("[name='newsintr']").val(news.newsintr);
            },
            error:function(){
                alert("回显出错");
            }
        })
    }
    //修改
    function updateNews(){
        $.ajax({
            url:"<%=request.getContextPath()%>/news/updateNews",
            type:"post",
            data:$("#updateuser").serialize(),
            dataType:"text",
            async:false,
            success:function(){
                location.reload();
            },
            error:function(){
                alert("修改出错");
            }
        })
    }
    //用freeMaker
    function updateXin(xid){

        BootstrapDialog.show({
            title : "修改的方法",       //title
            message :$('<div></div>').load("<%=request.getContextPath()%>/news/queryXin?xid="+xid),
            buttons : [{
                label : "修改",
                action : function(dialog){
                    $.ajax({
                        url : "<%=request.getContextPath()%>/xin/updateXin",
                        type : "post",
                        data : $("#XinUpdateForm").serialize(),
                        dataType:"json",
                        success : function(flag){
                            if(flag == 1){
                                location.reload();
                            }
                        }
                    })
                }
            },{
                label : "取消",
                action : function(dialog){
                    dialog.close();
                }
            }]
        })
    }

    //用poi导出word文档
    function importNews(){
        location.href="<%=request.getContextPath()%>/news/importNews";
    }


</script>
</body>
</html>


