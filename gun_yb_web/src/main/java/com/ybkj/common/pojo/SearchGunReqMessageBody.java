/**  
 * All rights Reserved, Designed By www.tct.com
 * @Title:  SearchGunReqMessageBody.java   
 * @Package com.tct.codec.protocol.pojo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 泰源云景科技     
 * @date:   2018年10月30日 下午8:00:53   
 * @version V1.0 
 * @Copyright: 2018 www.tct.com Inc. All rights reserved. 
 * 注意：本内容仅限于泰源云景科技内部传阅，禁止外泄以及用于其他的商业目
 */
package com.ybkj.common.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**   
 * @ClassName:  SearchGunReqMessageBody   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 泰源云景
 * @date:   2018年10月30日 下午8:00:53   
 *     
 * @Copyright: 2018 www.tct.com Inc. All rights reserved. 
 * 注意：本内容仅限于泰源云景科技有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */

@Data
public class SearchGunReqMessageBody {
	private String reserve;//预留字
	private String lo;//经度
	private String la;//纬度
	private List<GunInfo> lostGunList;//枪支列表信息
	private String authCode;//授权码

	@Data
	public static class GunInfo{
		private String gunMac;//枪支MAC号
		private String gunId;//枪支ID
		private String lostTime;//丢失时间
		
	}
}
