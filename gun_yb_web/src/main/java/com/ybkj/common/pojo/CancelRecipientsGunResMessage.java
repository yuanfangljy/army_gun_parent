/**  
 * All rights Reserved, Designed By www.tct.com
 * @Title:  CancelRecipientsGunResMessage.java   
 * @Package com.tct.codec.protocol.pojo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 泰源云景科技     
 * @date:   2018年10月30日 下午5:27:47   
 * @version V1.0 
 * @Copyright: 2018 www.tct.com Inc. All rights reserved. 
 * 注意：本内容仅限于泰源云景科技内部传阅，禁止外泄以及用于其他的商业目
 */
package com.ybkj.common.pojo;

import lombok.Data;

/**   
 * @ClassName:  CancelRecipientsGunResMessage   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 泰源云景
 * @date:   2018年10月30日 下午5:27:47   
 *     
 * @Copyright: 2018 www.tct.com Inc. All rights reserved. 
 * 注意：本内容仅限于泰源云景科技有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */

@Data
//10号报文
public class CancelRecipientsGunResMessage extends TemplateMessage {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -1515013617569987011L;

	private CancelRecipientsGunResMessage messageBody;
}
