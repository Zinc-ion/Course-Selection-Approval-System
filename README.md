# Course-Selection-Approval-System
本项目为学生选课审批系统，可对学生提出的课程申请进行审批，并可创建自定义审批流进行审批

本地环境：
集成开发工具：	 IDEA 2022.3.2
数据库：		 MySQL 8.0.31
操作系统：		 Windows 10
Web服务器：	 Tomcat 9.0.45
项目管理工具: 	 Maven 4.0
持久化层框架: 	 MyBatis 3.5.13
日志记录框架: 	 Log4j-core 2.20.0
前端开发框架: 	 BootStrap 3.4.1
测试框架：		 JUnit 4.13.2

# 安装说明：
将项目拉取下来后本地创建数据库，将项目根目录中java-login.sql数据库文件导入数据库，然后修改项目文件中\src\main\resources\db.properties内容为本机数据库信息即可

# 使用说明：
![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/fcbcf695-f932-4c5e-94e2-77473b5ec9a4)

进入系统首页后，用户需要输入账户名与密码，用户点击登录按钮后，系统前端通过jQuery设置按钮点击事件，首先检查用户输入是否为空值，如为空则提醒用户，账户名和密码都非空则提交表单交给后端servlet处理。

后端controller层LoginServlet接收到前端post请求后，首先从request中解析出用户输入的账户名与密码，然后交由service层的LoginCheck类检查数据库中是否有该用户，并返回用户类型。

## 学生
当用户身份被识别为学生后，将被定向到学生主页

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/e5a3d45b-9842-4eb0-913a-001349118555)

当学生点击选择课程后，将被定向到选择课程页面

 ![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/f1ecd3f3-0914-4eee-afcf-6afefe62cecf)
 
在此处顶部课程下拉栏中将会显示该学生可以选择的课程与课程信息（尚未被学生申请的课程，在学生点击选择课程按钮时检索，并存入session中），学生可以通过下拉栏选择课程，填写申请原因，点击选择文件上传申请证明，然后点击提交申请，网页前端会首先检查学生是否输入申请原因，如申请原因为空将会提醒学生。

原因非空时将会提交表单到controller层的SubmitACServlet，SubmitACServlet会首先调用isMultipartContent方法查看表单中是否含有文件，没有则返回申请页面并提醒用户未提交申请文件。

学生提交申请完毕后，将自动跳转到学生页面首页，学生点击申请进度查询后，前端使用ajax向后端ApplicationQueryServlet请求数据，并在url中携带type参数，表明自己请求的查询类型。若返回数据有误则提示用户查询异常，ajax连接异常。

在后端中，通过解析前端传来type参数分类处理查询，将用户id传给Service层的approvalService来查询用户名下申请的课程，并将返回的数据通过Gson类封装成json数据返回给前端，实现页面局部刷新，以列表显示该学生申请的课程

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/28a6a47d-2f6a-45e4-b0aa-0578e2d87eae)

审批结束后，审批状态为成功或者驳回时，学生点击申请进度查询后该申请的课程名将自动出现在页面右下角的确认下拉栏中，学生可以选择并点击确认，完成本次申请，前端通过对按钮设置onclick方法来提交表单至后端CompleteACServlet，同时确认选项是否为空并提醒用户，若非空则修改申请状态，完成申请。

结束本次申请后，在进度查询功能中不能再看到该条申请信息。被驳回申请的课程，学生在修改下拉栏中选择被驳回的申请并点击修改申请按钮，页面将跳转至修改申请页面。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/c9d030ad-738a-4d48-ac72-4dbab96efc97)

学生点击申请驳回记录查询或申请通过记录查询后，前端利用ajax向controller层的applicationQueryServlet发生请求，同时在url上分别附上不同的type数据，用于在servlet中区分查询类型，当type等于crjacq时，servlet会调用ApprovalService进行申请驳回记录查询，type=cagacq则会进行申请通过记录查询。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/522188ca-7997-444c-bee0-c34baa88df08)

查询完毕后，将会使用Gson类将请求list封装成json格式传输回前端，用于局部刷新展示，同时在前端列表上方会有msg提示用户当前的查询类型与共查询到多少条数据。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/2160d92f-f77a-45da-9e8f-60a990adcfb5)

当用户点击某种查询后，可在页面右上角搜索状态栏中进行多条件搜索，当用户输入状态后点击搜索按钮，此时前端页面会首先检查用户是否输入了值，若为空则不进行任何查询，若非空则会触发按钮点击事件。

## 教师
当用户登录被识别为教师时，会进一步细分识别为主讲教师与主管教师，主讲教师与主管教师会被导向同一页面，但页面标题会根据主讲教师和主管教师而变化。

 ![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/b0844da2-965d-4bd7-b2f9-17780048f9a7)

 
学生提交课程申请后，课程状态会首先变为申请已提交状态，只有该课程所属的主讲教师登录账户并查询待审批项时才会使该课程申请状态变为课程主讲教师审批中，主讲教师点击待审批查询后，下方列表将会显示属于该教师的待审批的申请，同时下方下拉栏也会显示对应申请的选项，主讲教师可以选择并点击同意申请，这时前端会自动提交表单到controller层的AgApprovalServlet在此处会根据用户登录时存储的session来区分用户是主管教师还是主讲教师。

如果已提交的申请不符合申请条件，可以驳回，教师可以通过下拉栏选中不符合条件的申请，填写驳回原因后点击驳回申请按钮，前端会首先会检查教师是否填写了驳回原因，若没有则会提醒教师。

当教师使用多条件查寻时，需在页面右上方搜索框内输入要查询的对应信息。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/3cb00e6e-e789-4a92-a226-804e0afa3d64)

当用户被识别为管理员时，系统自动跳转至管理员界面，此界面使用ajax局部页面刷新实现左侧固定菜单栏和顶部导航栏，加右侧动态子页面的展示方式，用于管理员管理系统内容。

 ## 管理员
当管理员想要管理系统用户时，只需点击左侧菜单栏用户管理按钮，展开用户管理选项，再点击用户列表按钮，前端会通过ajax来将右侧内容栏局部刷新，将userManage.jsp页面加载到此manage.jsp页面的右侧。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/ef3cab11-e848-4646-b991-c3393d6ceadd)

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/852e9ba7-bec5-41c8-8d5d-362d38d98a93)

管理员点击页面左上角新增用户按钮，页面即跳转到注册系统界面。

 ![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/16bfcc97-ef1a-47f8-a0a3-039c4234b81d)

在此处管理员可以填写用户ID用户密码，邮箱等信息，同时需要在下拉栏中选择用户身份，点击注册后完成新增用户，后台servlet会首先查询用户列表，查询用户ID是否重复，如重复则提醒用户并返回注册页面。

管理员选择用户并点击修改用户后，页面将跳转至用户修改界面。

 ![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/1e9966c5-7b93-4f7f-9aa4-e6189de156af)

页面上方会自动显示用户原信息，管理员可在下方填入用户修改后的密码，邮箱，职业等信息，前端通过onFocus与onBlur方法实现不填新信息则维持原信息不动，填入新信息，则以改变原信息的功能，方便用户操作。

当管理员想要管理课程时，只需点击左侧菜单栏课程管理按钮，展开课程管理选项，再点击课程列表按钮，前端会通过ajax来将右侧内容栏局部刷新，将courseManage.jsp页面加载到此manage.jsp页面的右侧。

 ![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/8f7ebdce-efba-4164-bf56-6eeb8f614dd9)

与用户管理逻辑一致，管理员可通过点击课程列表查询来局部刷新列表显示课程列表，也可以点击新增课程跳转至新增页面来新增课程，后台也会自动查看课程id是否重复。

当管理员想要修改教师与课程的对应管理关系时，需要在修改课程下拉栏中选择课程，点击修改课程按钮，页面将跳转到modifyCourse.jsp页面。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/0fac914b-17c3-4045-8d1b-6057ec5c8dca)

上方将显示原课程信息，管理员可在下方修改课程信息，下方主讲和主管教师下拉栏中将显示用户列表中的主讲和主管教师名列表，管理员可选择来修改教师与课程的对应管理关系。
当管理员想要查询审批记录、导出已审批通过记录时，需要在审批管理下拉栏中选择审批记录查询，页面将跳转到approvalRecordQuery.jsp页面。

点击审批记录列表查询即可在下方列表查看所有审批记录，通过ajax与后端传值实现，同时附带后端物理分页展示。

当管理员想要导出已审批通过记录时，可以点击页面下方导出已审批通过记录按钮，前端通过onClick方法跳转至后端DownloadApprovalRecordServlet下载已审批通过记录，servlet将首先调用ApprovalService类中方法查询记录并将记录保存在list中，随后调用utils包中ExcelUtil类中createExcel方法，使用apache.poi库中方法生成excel文件，并将list中数据填入其中，随后servlet将设置相应头让浏览器下载此excel文件，完成导出。

当管理员想要动态创建课程审批流程时，需要在审批管理下拉栏中选择审批流管理，右侧页面将刷新为approvalManage.jsp页面。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/78dc7a3d-38a2-4e2a-ba95-7f6608365116)

管理员可在此处将课程审批流由普通审批流转换为动态审批流，当管理员选择完课程后，可在左侧下拉栏中向审批流中添加或删除主讲教师，或在右侧下拉栏中添加或删除主管教师，最终审批流顺序将按照主讲教师由上至下审批，再到主管教师由上至下审批，若主讲或主管教师列表中为空则直接跳过该环节，进入下一状态。

具体实现是由数据库中tb_da_lt与tb_da_st两个表配合tb_approval表中最后4个字段完成的。

![image](https://github.com/Zinc-ion/Course-Selection-Approval-System/assets/96803357/2e33efcd-6f5f-48be-9af7-8b1282aef463)

当管理员向某课程审批流中添加教师时，该课程名与教师名将会一起被添加至主讲或主管教师表中，同时自动添加自增字段order，在随后的动态审批流程中将按照表中同一课程名下教师order由低到高的次序进行审批，并在approval的lt，st字段存储当前正在审批的主讲，主管教师order。若申请被驳回，则会在rjlt与rjst字段中存入驳回教师的order，用于各教师的记录查询。以此实现动态审批流程。


