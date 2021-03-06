/*
 * $Id: UserInfo.java,v 1.3 2006/04/09 11:57:04 laddi Exp $
 * Created on 31.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.block.family.presentation;

import java.rmi.RemoteException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.core.accesscontrol.business.LoginDBHandler;
import com.idega.core.accesscontrol.data.LoginTable;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.IWUserContext;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.Table;
import com.idega.presentation.text.Text;
import com.idega.user.business.UserBusiness;
import com.idega.user.business.UserSession;
import com.idega.user.data.User;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.StringUtil;


/**
 * Last modified: $Date: 2006/04/09 11:57:04 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.3 $
 */
public class UserInfo extends Block {

	private static String BUNDLE_IDENTIFIER = "is.idega.idegaweb.member";

	protected IWBundle iwb;
	protected IWResourceBundle iwrb;
	
	private Image iAccountImage;

	@Override
	public void main(IWContext iwc) {
		this.iwb = getBundle(iwc);
		this.iwrb = getResourceBundle(iwc);
		
		try {
			User user = null;
			String userId = iwc.getParameter(UserEditor.PRM_USER_ID);
			if (StringUtil.isEmpty(userId)) {
				user = getUserSession(iwc).getUser();
			} else {
				UserBusiness userBusiness = IBOLookup.getServiceInstance(iwc, UserBusiness.class);
				user = userBusiness.getUser(Integer.valueOf(userId));
			}
			if (user != null) {
				Table table = new Table(3, 1);
				table.setCellpadding(4);
				table.setWidth(Table.HUNDRED_PERCENT);
				table.setAlignment(3, 1, Table.HORIZONTAL_ALIGN_RIGHT);
				table.setWidth(1, "150");
				
				table.add(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()), 1, 1);
				
				table.add(user.getLastName() + ", ", 2, 1);
				Text firstName = new Text(user.getFirstName());
				firstName.setBold();
				table.add(firstName, 2, 1);
				if (user.getMiddleName() != null) {
					table.add(" " + user.getMiddleName(), 2, 1);
				}
				
				LoginTable lt = LoginDBHandler.getUserLogin(((Integer) user.getPrimaryKey()).intValue());
				if (lt != null && this.iAccountImage != null) {
					table.add(this.iAccountImage, 3, 1);
				}
				
				add(table);
			}
		}
		catch (RemoteException re) {
			log(re);
		}
	}
	
	protected UserSession getUserSession(IWUserContext iwuc) {
		try {
			return (UserSession) IBOLookup.getSessionInstance(iwuc, UserSession.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	@Override
	public String getBundleIdentifier() {
		return BUNDLE_IDENTIFIER;
	}
	
	public void setAccountImage(Image accountImage) {
		this.iAccountImage = accountImage;
	}
}