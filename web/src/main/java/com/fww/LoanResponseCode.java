package com.fww;


/**
 * 响应给客户端编码，由8位组成：业务系统逻辑区分码（1位）+系统码（2）+模块码（2位）+错误码（3位）
 * 
 * 一、公共编码采用递增编码
 * 二、逻辑区分码:1开头表示系统运行时异常，2开头表示业务异常
 * 三、系统码：规定如下
 *   1.贷款系统：       	 01
 *   2.财务系统：      	     02
 *   3.风控系统：        	 03
 *   4.后台管理系统：	 04
 * 四、模块码：各系统自定义，用2位数表示
 * 五、错误码：各系统自定义，采用3位数表示
 * 
 * 
 * 模块编码:
 *   00：业务进件列表模块
 *   01：业务进件模块
 *   02：产品配置模块
 *   03：车辆评估
 *   04：资料上传
 *   05：门店初审
 *   06：放款申请
 *   07: 结清审核
 *   08: 基础信息模块
 *   09：合同设置
 *   10：抵押权人，债权人，收款账户，中介列表
 *   11：门店复审
 *	 12：远程初审
 *	 13：总部终审、总部放款确认
 * 
 * @author YangPH
 *
 */
public class LoanResponseCode extends BaseResponseCode {
	/**
	 * 贷款系统运行时异常响应码枚举
	 * 
	 * @author YangPH
	 *
	 */
	public enum LoanExpResCodeEnum {
		
		EXP2(10100002, "程序异常2");

		private int code;
		private String msg;

		private LoanExpResCodeEnum(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getValue() {
			return code;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

	}
	
	/**
	 * 贷款系统业务异常响应码枚举
	 * 
	 * @author YangPH
	 *
	 */
	public enum LoanBizResCodeEnum {
		ACCEPT_ORDER_FINISHED(20100001, "订单已被认领完"),
		NOT_ACCEPT_ORDER(20100002, "不包含该认领类型"),
		ORDER_ACCEPTED(20100003, "改订单已被认领，请刷新列表重新认领"),
		
		/** 产品配置 */
		PRO_IS_NULL(20102001, "产品数据为空"),
		EXISTS_PRO_CODE(20102002, "产品编号已存在"),
		EXISTS_PRO_NAME(20102003, "产品名称已存在"),
		PRO_PARAMS_IS_ERROR(20102003, "产品参数之间，借款期限、还款方式、代收服务加盟商收取方式、代收个人服务费收取方式不能同时相同"),
		/** 车辆评估 */
		BIZINTO_UNFINISHED(20103001,"进件信息未填,请先完善进件信息"),
		CHECK_PROTYPE(20103002,"该笔订单已被远程初审认领，只能选择远程产品，请检查选择产品"),
		CHECK_AUDIT_DEPART(20103005,"不能够选择跨部门审批产品"),

		CHECK_STROE_REVIEW(20103003,"该笔订单已被门店复审通过，不可再进行保存操作"),		
		REG_DATE_ILLEGAL(20103004,"日期格式不正确"),
		CHE_300_RETURN_CODE(20103009,"车300查询价格失败"),	
		PARKFEE_UNFINISHED(20105001,"gps费或停车费未填,不能提交"),
		BOTH_ID_IS_NULL(20100004,"bizId不能为空"),
		BREACH_FEE_REDUCE_EXC(20100005,"保存违约金减免审批单参数异常"),
		UN_SETTLE(20107001,"未申请结清,无法查询结算费用明细"),		
		CANNOT_SUBMIT(20107002,"财务实收少于应收，不予结清！"),
		UN_SETTLE_NOTAUDIT(20107003,"未申请结清,无法进行结清审核"),
		SETTLE_BIZID_NULL(20107004,"查询结清审核列表 bizId为null"),			
		UN_COMPELTE_BREACH_FEE_REDUCE(20107005,"未填写违约金减免审批单,无法减免提交"),		
		UN_FINISH_MUST_WRITE(20107006,"违约金减免审批单必填项未填写"),		
		BANK_NAME_BEING(20108002,"银行名称已存在"),
		BANK_CODE_BEING(20108001,"银行代码已存在"),		
		BANK_VERIFY_SUCCESS(20106000,"银行卡验证:成功"),
		BANK_VERIFY_FAIL(20106001,"银行卡验证:失败"),
		BANK_VERIFY_QUERY(20106002,"银行卡验证:查询中"),
		BANK_VERIFY_NO_RESULT(20106003,"银行卡验证:按照条件查询无结果"),
		BANK_VERIFY_EXP(20106004,"银行卡验证:程序异常"),
		BANK_VERIFY_NOT_SUPPORT(20106005,"银行卡验证:不支持的银行卡"),	
		UPDATE_BANK_EXP(20106006,"更新银行卡信息参数异常"),		
		CHECK_BANK_CODE_FAIL(20106007,"查询银行代码失败,请检查银行账号是否正确"),	
		QUERY_OVERDUE_REDUCE_INFO_FAIL(20107001,"查询违约金减免审批单基本数据失败"),			
		RECEIVE_BANK_NOT_CONGRUITY(20103002,"收款银行与收款账号对应银行不一致，请检查银行或银行代码是否正确"),
		STORE_SEC_COMPLETED(20111001,"门店复审已提交,无法保存"),		
		STORE_RISK_UNFINISHED(20111002,"门店风控报告未保存,无法提交初审意见"),		
		REMOTE_FIR_COMPLETED(20112001,"远程初审已提交,无法保存"),
		APPLY_LOAN_CARD(20113001,"放款申请账号不能为空"),
		APPLY_LOAN_BANK(20113002,"放款申请所属银行不能为空"),


		NOT_ADD_LOAN_ONE(20114001,"无权限，请由同一业务员操作"),
		NOT_ADD_LOAN_TWO(20114002,"该车牌号无在贷记录，客户类型请选择“新增”或“结清在贷”"),
		NOT_ADD_LOAN_THREE(20114003,"该车牌号无在贷记录，客户类型请选择“新增”或“结清在贷”"),
		NOT_ADD_LOAN_FOUR(20114004,"该车牌号已有增贷记录，不可再增贷"),
		NOT_LOAN_INTO(20114005,"该车牌号已有在贷记录，不可再贷，请等待结清之后再贷"),



		/** 文件管理  */
		IMPERFECT_DATA(20104001,"上传文件资料不完整"),
		FILE_DELETE_FAIL(20104002, "删除文件失败,缺少必传的参数"),
		PRO_TYPE_ID_IS_NULL(20104003, "产品类型为空,无法校验文件资料是否完整"),

		/** 合同设置  */
		CONTRACT_TYPE_IS_NULL(20109001, "合同打印设置为空"),
		CONDITION_PARAM_NAME_IS_ERROR(20109002, "合同条件设置参数名错误"),
		CONTRACT_TEMP_PARAM_IS_ERROR(20109003, "合同设置输入参数错误，请按规定输入"),
		CONTRACT_TEMP_IS_NULL(20109004, "合同为空"),
		EXISTS_CONTRACT_KEY(20109005, "合同key值已存在"),
		CONTRACT_TYPE_PARAM_IS_ERROR(20109006, "合同条件设置参数名错误"),
		
		/** 抵押权人，债权人，收款账户，中介列表  */
		IDCARD_IS_EXISTS(20110001, "身份证号已存在"),
		MOBILE_IS_EXISTS(20110002, "手机号码已存在"),
		INFO_OF_PEOPLE_IS_ERROR(20110003, "输入的数据不正确"),
		BANK_NO_IS_EXISTS(201100024, "银行卡号已存在"),
		
		/** 总部终审、总部放款确认退回异常提示  */
		HEAD_AUDIT_BACK_EXP(20113001, "退回类型勾选错误"),



		BIZID_IS_NULL(20203001,"bizId不能为空"),
		UP_REAPY_PLAN_STATUS(20203002,"更新还款计划结清状态失败"),
		DEDUCT_SUCCESS(20203003,"划扣成功"),
		DEDUCTING(20203004,"正在划扣中，成功后状态会自动变更为成功"),
		REPAY_BEING(20203005,"该期已还款"),
		DEDUCT_FAIL(20203006,"划扣失败"),
		TL_DEDUCT_EXC(20203007,"通联调用划扣异常"),
		TL_SINGLE_DEDUCT_EXC(20203008,"通联单笔划扣添加划扣记录异常"),
		TL_SINGLE_DEDUCT_UPDATEPLAN_EXC(20203009,"通联单笔划扣修改还款计划异常"),
		DEDUCTING_NOT_PAY(20203010,"该期划扣中不可操作"),
		TL_DEDUCT_UPDATE_EXC(20203011,"通联批量划扣修改还款计划为划扣中异常"),
		TL_RETRY_DEDUCT_UPDATE_EXC(20203012,"通联批量重试划扣异常"),
		DEDUCTED_NOT_PAY(20203013,"该期划扣成功不可重复划扣"),
		SETTING_DEDUCT_NOT_PAY(20203014,"该单已结清不可划扣"),
		DEDUCT_RECORD_NULL(20203015,"按条件查询划扣记录为空"),
		DEDUCT_NOT_SETTLE(20203016,"划扣中不可申请结清"),
		HAD_BEEN_SETTLED(20203017,"已申请结清不可重复申请结清"),
		DEDUCT_AMOUNT_IS_NULL(20203018,"划扣金额不能为空"),
		SETTLE_NOT_IN_DEDUCT_STEP(20203019,"该业务的结清阶段为“门店财务收款查账”或“总部财务复核款项"),

		/** 放款 */
		NO_MORE_ORDER_TO_ACCEPT(20201001, "没有待认领订单"),
		NO_SEARCH_DATA(20201002, "没有导出数据"),
		ACTLENDAMOUNT_LESS_THAN_ZERO(20201003, "本次放款金额小于零"),
		SEND_BACK_UPDATE_PROCESS_EXP(20201004, "放款退回时更新流程状态发生异常，请再次发起退回或联系系统管理员"),
		UPDATE_REPAY_PLAN_FAILED(20201005, "放款后更新还款记录失败"),
		LEND_OR_SENDBACK_EXP(20201006, "退回或放款发生异常，请联系系统管理员核查异常信息"),
		LEND_IS_PROCESSED(20201007, "该放款订单处于不允许放款状态"),
		GET_LEND_DETAIL_FAIL(20201008, "查询银行卡失败或放款订单详情获取失败，请再次请求或联系管理员"),
		UPDATE_LEND_FAIL(20201009, "更新放款单失败，请再次请求或联系管理员"),
		HKMACAOTW(20201010, "香港客户不能够使用通联放款"),


		/** 还款  */
		MONEY_IS_ERROR(20205001, "金额输入有误，请仔细核对数据"),
		CAN_NOT_USER_BALANCE(20205002, "本期未还清时不能使用账户余额，请核对数据"),
		BALANCE_CHANGE_IS_ERROR(20205003, "余额变更金额异常"),
		BALANCE_IS_NOT_ENOUGH(20205004, "余额不足"),
		TL_ADD_DEDUCT_RECORD_EXC(20205005, "通联批量划扣处理成功，批量添加划扣记录异常"),
		SUM_NOT_DEDUCT(20205006, "划扣金额为0不可划扣"),
		SHOULD_REPAY_IS_ERROR(20205007, "应还金额项不是最新数据"),
		BALANCE_IS_ENOUGH(20205008, "当前余额充足，应使用余额还款"),


		/** APP产品设置  */
		MIN_AMOUNT_BIGGER_THER_MAX_AMOUNT(20207001,"贷款限制最小额度大于最大额度"),

		/** APP版本设置  */
		APP_VERSION_CONFIG_IS_NULL(20208001,"APP版本设置为空"),

		/** 客户贷款申请  */
		ONLY_IN_NEEDTODO_THEN_CHANGE_REFUSED(202009001,"只有在待办理状态才能转成拒绝状态");

		private int code;
		private String msg;

		private LoanBizResCodeEnum(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getValue() {
			return code;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

	}

}
