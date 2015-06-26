package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.SystemLanguageTbl;

/**
 * Model class for SystemLanguage. Add not database mapped fileds in this class.
 */
public class SystemLanguage extends SystemLanguageTbl {

	private String languageName;

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

}
