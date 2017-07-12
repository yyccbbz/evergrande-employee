#----------------------------------------#
#------QUERY INFOMATION by Markting------#
#----------------------------------------#

##查询条件（可多选，支持模糊查询）																														
#姓名、证件号、手机号、EMS号、员工ID

##输出																														
#姓名	身份证号	手机号	员工编码	性别	年龄	入职时间	离职时间	是否VIP	服务年限	公司名	在职状态	特殊名单类型	是否特殊名单	
#贷款申请流水号	贷款申请日期	当前贷款状态	剩余未还本金	员工住址	员工收入情况	申请金额	审批完成时间	是否审批拒绝	审批拒绝原因	审批金额
#签约时间	签约金额	放款时间	放款金额	贷款类型（期数）	费率

#drop table bigdata_ecf_bi.wy_temp;
#create table bigdata_ecf_bi.wy_temp as
select
 a.employee_name                                      #姓名
,a.idcard                                             #身份证号
,a.mobile_phone                                       #手机号
,a.ems_id                                             #EMS号
,a.employee_id                                        #员工编码
,a.sex                                                #性别
,a.age                                                #年龄
,a.entry_date                                         #入职时间
,coalesce(a.quit_date,'') as quit_date                #离职时间
,a.is_vip                                             #是否VIP
,a.grp_service_years                                  #服务年限
,a.company_name                                       #公司名
,a.service_status                                     #在职状态
,a.special_title                                      #特殊名单类型
,a.is_special                                         #是否特殊名单
,b.serialno                                           #贷款申请流水号
,c.inputtime                                          #贷款申请日期
,b.phasename                                          #贷款状态
,b.loan_unfinish_amt                                  #贷款剩余应还本金
,c.familyAddress                                      #员工住址
,c.salary                                             #员工收入情况
,c.businesssum as apply_amt                           #申请金额
,c.app_endtime                                        #审批完成时间
,c.is_app_reject                                      #是否审批拒绝
,c.app_reject_reason                                  #审批拒绝原因
,coalesce(d.businesssum      ,'') as approval_amt     #审批金额	
,coalesce(d.signdate         ,'') as signdate         #签约时间
,coalesce(d.businesssum      ,'') as sign_amt         #签约金额
,coalesce(e.batchdate        ,'') as putoutdate       #放款时间
,coalesce(e.putoutamt        ,'') as putoutamt        #放款金额
,coalesce(d.businessterm     ,'') as businessterm     #贷款类型（期数）
,coalesce(d.businessrate_cut ,'') as businessrate_cut #费率-期初服务费
,coalesce(d.businessrate_rate,'') as businessrate_rate#费率-年利率
,coalesce(d.businessrate_fee ,'') as businessrate_fee #费率-月管理费
,coalesce(d.businessrate_fin ,'') as businessrate_fin #费率-罚息

from bigdata_ecf_bi.t_evergrande_employee_tmp1 a
join(
#贷款状态
#贷款剩余应还本金
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

join(
#贷款申请日期
#员工收入情况
#申请金额    
#审批完成时间
#是否审批拒绝
#审批拒绝原因
select d.serialno
      ,d.inputtime
      ,d.businesssum
      ,d.customername
      ,REPLACE(json_extract(b.base_material,'$.familyAddress'),'"','') as familyAddress
      ,REPLACE(json_extract(b.income_and_assets,'$.salary'),'"','') as salary
      ,case when e.phaseno='8000' then c.plan_status_reason else '' end as app_reject_reason
      ,case when e.phaseno='8000' then '是' else '否' end as is_app_reject
      ,e.endtime as app_endtime
from ecf_ofc.t_ofc_loan_application a
join ecf_ofc.t_ofc_material_entry_img b on a.loan_plan_no=b.loan_plan_no
join ecf_ofc.t_ofc_loan_plan c on a.loan_plan_no=c.loan_plan_no
join ecf_webapp.business_apply d on a.loan_application_no=d.appserialno
join ecf_webapp.flow_task e on d.serialno=e.objectno
where e.objecttype='jbo.app.BUSINESS_APPLY'
  and e.phaseno in ('1000','8000')
#ecf_webapp.de_transaction_log where TRANSACTIONid like '%HDJF_13%' and requestmsg like '%王一%'
)c on b.serialno=c.serialno

left outer join(
#审批金额	
#签约时间
#签约金额
#贷款类型（期数）
#期初服务费率
#年利率
#月管理费
#罚息
select c.serialno
      ,a.businesssum
      ,b.signdate
      ,a.businessterm
      ,d.businessrate as businessrate_cut
      ,e.businessrate as businessrate_rate
      ,f.businessrate as businessrate_fee
      ,g.businessrate as businessrate_fin
from ecf_webapp.BUSINESS_CONTRACT a
join ecf_webapp.BUSINESS_CONTRACT_SIGN b on a.SERIALNO=b.OBJECTNO
join ecf_webapp.business_apply c on b.objectno=c.serialno
join(select OBJECTNO, businessrate from ecf_webapp.acct_rate_segment where OBJECTTYPE='jbo.app.BUSINESS_CONTRACT' and TERMID='CUT01')d on c.SERIALNO=d.OBJECTNO
join(select OBJECTNO, businessrate from ecf_webapp.acct_rate_segment where OBJECTTYPE='jbo.app.BUSINESS_CONTRACT' and TERMID='RAT02')e on c.SERIALNO=e.OBJECTNO
join(select OBJECTNO, businessrate from ecf_webapp.acct_rate_segment where OBJECTTYPE='jbo.app.BUSINESS_CONTRACT' and TERMID='FEE01')f on c.SERIALNO=f.OBJECTNO
join(select OBJECTNO, businessrate from ecf_webapp.acct_rate_segment where OBJECTTYPE='jbo.app.BUSINESS_CONTRACT' and TERMID='FIN02')g on c.SERIALNO=g.OBJECTNO
where b.OBJECTTYPE='jbo.app.BUSINESS_CONTRACT'
  and b.SIGNDATE is not null
  and b.status='03'
)d on b.serialno=d.serialno

left outer join(
#放款时间
#放款金额
select c.serialno
      ,a.batchdate
      ,a.putoutamt
      ,c.customername
from ecf_webapp.LOAN_BATCH_INFO a
join ecf_webapp.acct_loan b on a.objectno=b.putoutserialno
join ecf_webapp.business_apply c on b.applyserialno=c.serialno
where a.batchtype='10'
  and a.objecttype='jbo.app.BUSINESS_PUTOUT'
  and a.transstatus  in ('11','14','15')
)e on b.serialno=e.serialno

where a.employee_name like '%%'
and a.idcard='' #证件号
and a.mobile_phone='' #手机号
and a.ems_id #EMS号
and a.employee_id #员工ID
;

