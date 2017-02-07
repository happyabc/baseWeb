<%--
  Created by IntelliJ IDEA.
  User: ChinaJoy
  Date: 2017/1/23
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>信息中心考勤系统</title>

    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.5.1/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.5.1/themes/icon.css"/>
    <script type="text/javascript" src="js/jquery-easyui-1.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js"></script>
    <script language="JavaScript">
        $('#deptTree ul').tree({
            onClick: function (node) {
                alert('fsfdsf');
                //alert(node.attributes.url);  // alert node text property when clicked
                // add a new tab panel  addTab('local','dept/index.jsp')
//                $('#tt').tabs('add', {
//                    title: node.text,
////                    href: node.attributes.url,
//                    href: '?menu',
//                    closable: true
//
//                });

            }
        });
        //添加Tabs
        function addTabs(event, treeId, treeNode, clickFlag) {
            if (!$("#tt").tabs('exists', treeNode.name)) {
                $('#tt').tabs('add', {
                    id: treeId,
                    title: treeNode.name,
                    selected: true,
                    href: treeNode.dataurl,
                    closable: true
                });
            } else $('#tt').tabs('select', treeNode.name);
        }

        //删除Tabs
        function closeTab(menu, type) {
            var allTabs = $("#tt").tabs('tabs');
            var allTabtitle = [];
            $.each(allTabs, function (i, n) {
                var opt = $(n).panel('options');
                if (opt.closable)
                    allTabtitle.push(opt.title);
            });

            switch (type) {
                case 1 :
                    var curTabTitle = $(menu).data("tabTitle");
                    $("#tt").tabs("close", curTabTitle);
                    return false;
                    break;
                case 2 :
                    for (var i = 0; i < allTabtitle.length; i++) {
                        $('#tt').tabs('close', allTabtitle[i]);
                    }
                    break;

            }

        }
        function addTab(title, url) {
            if ($('#tt').tabs('exists', title)) {
                $('#tt').tabs('select', title);
            } else {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
                $('#tt').tabs('add', {
                    title: title,
                    content: content,
                    closable: true
                });
            }
        }


        $(document).ready(function () {
            //监听右键事件，创建右键菜单
            $('#tt').tabs({
                onContextMenu: function (e, title, index) {
                    e.preventDefault();
                    if (index > 0) {
                        $('#mm').menu('show', {
                            left: e.pageX,
                            top: e.pageY
                        }).data("tabTitle", title);
                    }
                }
            });
            //右键菜单click
            $("#mm").menu({
                onClick: function (item) {
                    closeTab(this, item.name);
                }
            });
        });
    </script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false" style="height:60px;padding:10px">信息中心考勤系统</div>
<div data-options="region:'west',split:true,title:'功能菜单'" style="width:200px;padding:10px;">west content
    <div id="treeMenu" title="treeMenu" data-options="iconCls:'icon-search',url:stu/014_tree.jsp" style="padding:10px;">
        <a href="#" class="easyui-linkbutton" onclick="addTab('打卡日志','jsp/datagrid.jsp')">打卡日志</a>
        <a href="#" class="easyui-linkbutton" onclick="addTab('tree','jsp/tree.jsp')">tree </a>
        <a href="#" class="easyui-linkbutton" onclick="addTab('combogrid','jsp/combogrid.jsp')">combogrid </a>
        <a href="#" class="easyui-linkbutton" onclick="addTab('study','jsp/study.jsp')">study </a>
        <ul id="deptTree" title="deptTree" class="easyui-tree">
            <li>
                <span>Foods</span>
                <ul>
                    <li>
                        <span>Fruits</span>
                        <ul>
                            <li>apple</li>
                            <li>orange</li>
                        </ul>
                    </li>
                    <li>
                        <span>Vegetables</span>
                        <ul>
                            <li>tomato</li>
                            <li>carrot</li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>

</div>
<div data-options="region:'center',title:'主界面'">
    <div id="tt" class="easyui-tabs" style="width:100%;height:100%">

        <div title="首页" style="padding:10px">
            <ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true"></ul>
        </div>
    </div>
</div>
<div data-options="region:'east',split:true,collapsed:true,title:'说明'" style="width:100px;padding:10px;">east region
</div>
<div data-options="region:'south',border:false" style="height:30px;padding:10px;">
    <center>版权所有：<a href="http://www.orenda.com.cn/" target="_blank">奥伦达部落</a></center>
</div>
</body>
</html>