/*
 * $Id$
 * Created on Apr 5, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.block.family.data;



import javax.ejb.FinderException;

import com.idega.data.IDOFactory;
import com.idega.user.data.Group;


/**
 * <p>
 * TODO laddi Describe Type ChildHomeImpl
 * </p>
 *  Last modified: $Date$ by $Author$
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision$
 */
public class ChildHomeImpl extends IDOFactory implements ChildHome {

	protected Class getEntityInterfaceClass() {
		return Child.class;
	}

	public Child create() throws javax.ejb.CreateException {
		return (Child) super.createIDO();
	}

	public Child findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return (Child) super.findByPrimaryKeyIDO(pk);
	}

	public Child findUserForUserGroup(Group group) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ChildBMPBean) entity).ejbFindUserForUserGroup(group);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

}
