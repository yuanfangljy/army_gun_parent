/**  
 * All rights Reserved, Designed By www.tct.com
 * @Title:  CancelInWarehouseReqMessage.java   
 * @Package com.tct.codec.protocol.pojo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 泰源云景科技     
 * @date:   2018年10月30日 下午5:48:33   
 * @version V1.0 
 * @Copyright: 2018 www.tct.com Inc. All rights reserved. 
 * 注意：本内容仅限于泰源云景科技内部传阅，禁止外泄以及用于其他的商业目
 */
package com.ybkj.common.pojo;

import org.omg.CORBA.PRIVATE_MEMBER;

import lombok.Data;

/**   
 * @ClassName:  CancelInWarehouseReqMessage   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 泰源云景
 * @date:   2018年10月30日 下午5:48:33   
 *     
 * @Copyright: 2018 www.tct.com Inc. All rights reserved. 
 * 注意：本内容仅限于泰源云景科技有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */

@Data
//13号报文
public class CancelInWarehouseReqMessage extends TemplateMessage {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 5635223987395849566L;

	private CancelInWarehouseReqMessageBody messageBody;
}
