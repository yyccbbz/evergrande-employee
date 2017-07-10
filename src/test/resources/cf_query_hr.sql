#----------------------------------------#
#---------QUERY INFOMATION by HR---------#
#----------------------------------------#

##查询条件（可多选 仅姓名支持模糊查询）
#	姓名 证件号 手机号 EMS号 员工ID

##输出字段
#姓名
#身份证号
#手机号
#员工编码
#性别
#年龄
#入职时间
#离职时间
#服务年限
#公司名
#在职状态
#贷款状态
#贷款剩余应还本金
#离职管控方式


##离职管控方式	取值有两种：
#1.暂停办理离职手续，请与互金集团接口人XXX联系，并提醒员工结清贷款，
#  再行办理离职手续可办理离职手续；（适用于有在贷中、已审批未放款、申请中等状态员工贷的员工）
#2.可办理离职手续；（适用于除第1点之外情况的员工）

##离职管控取值判断
#根据进件台账的“所属流程阶段”和贷款台账的“贷款状态”两个字段取值判断，具体如下：

#1.若贷款申请未放款，则在进件台账里，根据“所属流程阶段”取值判断离职管控方式
#---------------------------
#所属流程阶段 |	离职管控方式
#---------------------------
#已取消申请	  	| 2.可办理离职手续
#已复核阶段	  	| 1.暂停办理离职手续……
#电话核查	    | 1.暂停办理离职手续……
#复核阶段	    | 1.暂停办理离职手续……
#人工复核	    | 1.暂停办理离职手续……
#人工初审	    | 1.暂停办理离职手续……
#已批准	      	| 1.暂停办理离职手续……
#代付初审	    | 1.暂停办理离职手续……
#记账复核	    | 1.暂停办理离职手续……
#人工终审	    | 1.暂停办理离职手续……
#申请阶段	    | 1.暂停办理离职手续……
#已否决	      	| 2.可办理离职手续
#放款复核	    | 1.暂停办理离职手续……
#待处理阶段	  	| 1.暂停办理离职手续……

#2.若已放款，则进入贷款台账中，根据“贷款状态”取值判断离职管控方式
#-----------------------
#贷款状态	| 离职管控方式
#-----------------------
#正常	    | 1.暂停办理离职手续……
#逾期	    | 1.暂停办理离职手续……
#正常结清	| 2. 可办理离职手续
#提前结清	| 2. 可办理离职手续
#逾期结清	| 2. 可办理离职手续
#已全部核销／已全部售出	| 2. 可办理离职手续
#已冲销	  | 2. 可办理离职手续

select
 a.employee_name    #姓名
,a.idcard           #身份证号
,a.mobile_phone     #手机号
,a.employee_id      #员工编码
,a.sex              #性别
,a.age              #年龄
,a.entry_date       #入职时间
,ifnull(a.quit_date,'') as quit_date#离职时间
,a.service_years    #服务年限
,a.company_name     #公司名
,a.service_status   #在职状态
,b.phasename        #贷款状态
,b.loan_unfinish_amt#贷款剩余应还本金
,b.leave_control    #离职管控方式
from bigdata_ecf_bi.t_evergrande_employee_tmp1 a
join(
select serialno
      ,customername
      ,certid
      ,begintime
      ,phasename
      ,loan_unfinish_amt
      ,case when phasename in (
                 '已复核阶段'
                ,'电话核查'
                ,'复核阶段'
                ,'人工复核'
                ,'人工初审'
                ,'已批准'
                ,'代付初审'
                ,'记账复核'
                ,'人工终审'
                ,'申请阶段'
                ,'放款初审'
                ,'放款复核'
                ,'待处理阶段'
                ,'正常'
                ,'逾期')
            then '暂停办理离职手续，请与互金集团接口人XXX联系，并提醒员工结清贷款，再行办理离职手续可办理离职手续；（适用于有在贷中、已审批未放款、申请中等状态员工贷的员工）'
            when phasename in (
                 '已取消申请'
                ,'已否决'
                ,'正常结清'
                ,'提前结清'
                ,'逾期结清'
                ,'已全部核销/已全部售出'
                ,'已冲销')
            then '可办理离职手续；（适用于除第1点之外情况的员工）'
            else 'Error!'
        end as leave_control

from(
select t.serialno
      ,t.customername
      ,t.certid
      ,t.begintime
      ,t.phasename
      ,t.loan_unfinish_amt
      ,@rownum:=@rownum+1
      ,if(@pdept=t.serialno,@rank:=@rank+1,@rank:=1) as rank
      ,@pdept:=t.serialno
from(
select t.*
from(
select a.serialno
      ,a.customername
      ,c.certid
      ,b.begintime
      #,b.endtime
      #,b.flowno
      #,b.phaseno
      ,b.phasename
      ,0 as loan_unfinish_amt
from ecf_webapp.business_apply a
join ecf_webapp.flow_task b on a.serialno=b.objectno
join ecf_webapp.customer_info c on a.customerid=c.customerid
where b.flowno in ('CreditFlow','PaymentPutOutFlow','TransactionFlow','PutOutFlow')

union all

select a.serialno
      ,a.customername
      ,d.certid
      ,DATE_FORMAT(NOW(),'%Y/%m/%d %H:%i:%s') as begintime
      ,c.itemname as phasename
      ,sum(ifnull(e.PAYPRINCIPALAMT,0)-ifnull(e.ACTUALPAYPRINCIPALAMT,0)) as loan_unfinish_amt
from ecf_webapp.business_apply a
join ecf_webapp.acct_loan b on a.serialno=b.applyserialno
join ecf_webapp.code_library c on b.loanstatus=c.itemno
join ecf_webapp.customer_info d on a.customerid=d.customerid
join ecf_webapp.ACCT_PAYMENT_SCHEDULE e on e.objectno=b.serialno
where c.codeno='LoanStatus'
group by a.serialno
      ,a.customername
      ,d.certid
      ,DATE_FORMAT(NOW(),'%Y/%m/%d %H:%i:%s')
      ,c.itemname
)t
order by serialno, begintime desc
)t,(select @rownum :=0 , @pdept := null ,@rank:=0)a
)t
where rank=1
)b on a.idcard=b.certid
where a.idcard='370923198601152215' #证件号
and a.mobile_phone='' #手机号
and a.ems_id='' #EMS号
and a.employee_id='' #员工ID
;




#select b.customername,a.*
#from ecf_webapp.LOAN_BATCH_INFO a
#join ecf_webapp.acct_loan b on a.objectno=b.putoutserialno
#join ecf_webapp.business_apply c on b.applyserialno=c.SERIALNO
#where batchtype='10'
#  and transstatus  in ('11','14','15')
#;
#
#SELECT c.serialno
#      ,sum(ifnull(a.PAYPRINCIPALAMT,0)-ifnull(a.ACTUALPAYPRINCIPALAMT,0)) as loan_unfinish_amt
#FROM ecf_webapp.ACCT_PAYMENT_SCHEDULE a
#join ecf_webapp.acct_loan b on a.objectno=b.serialno
#join ecf_webapp.business_apply c on b.applyserialno=c.serialno
#WHERE a.objecttype='jbo.acct.ACCT_LOAN'
#group by c.serialno
#;
#
#
#select 
#from ecf_webapp.LOAN_BATCH_INFO a
#join ecf_webapp.acct_loan b on a.objectno=b.putoutserialno
#join ecf_webapp.business_contract c on b.CONTRACTSERIALNO=c.SERIALNO
#where a.batchtype='10'
#  and a.objecttype='jbo.app.BUSINESS_PUTOUT'
#  and a.transstatus  in ('11','14','15')
#;