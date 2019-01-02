package com.sykj.shenfu.po;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 */
public class Userdispatchinstaller extends BaseField implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id; // 数据库ID
	private Integer dispatchcode; // 工单ID
	private Integer installercode; //安装人员编号
	private String remark; // 备注

	private Userdispatchinstaller userdispatchinstaller;
	private List<Userdispatchinstaller> userdispatchinstallerlist;
	
    private Userorder userorder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Userorder getUserorder() {
		return userorder;
	}

	public void setUserorder(Userorder userorder) {
		this.userorder = userorder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getDispatchcode() {
		return dispatchcode;
	}

	public void setDispatchcode(Integer dispatchcode) {
		this.dispatchcode = dispatchcode;
	}

	public Integer getInstallercode() {
		return installercode;
	}

	public void setInstallercode(Integer installercode) {
		this.installercode = installercode;
	}

	public Userdispatchinstaller getUserdispatchinstaller() {
		return userdispatchinstaller;
	}

	public void setUserdispatchinstaller(Userdispatchinstaller userdispatchinstaller) {
		this.userdispatchinstaller = userdispatchinstaller;
	}

	public List<Userdispatchinstaller> getUserdispatchinstallerlist() {
		return userdispatchinstallerlist;
	}

	public void setUserdispatchinstallerlist(
			List<Userdispatchinstaller> userdispatchinstallerlist) {
		this.userdispatchinstallerlist = userdispatchinstallerlist;
	}


}
