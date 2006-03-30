/*
 * $Id$
 * Created on Mar 29, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.block.family.data;

import is.idega.block.family.business.FamilyConstants;
import is.idega.block.family.business.FamilyLogic;
import is.idega.block.family.business.NoCustodianFound;
import is.idega.block.family.business.NoSiblingFound;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.data.IDOLookupException;
import com.idega.user.data.Gender;
import com.idega.user.data.GenderHome;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.user.data.UserBMPBean;


public class ChildBMPBean extends UserBMPBean implements User, Child {

	public static final String METADATA_GROWTH_DEVIATION = "growth_deviation";
	public static final String METADATA_GROWTH_DEVIATION_DETAILS = "growth_deviation_details";
	
	public static final String METADATA_ALLERGIES = "allergies";
	public static final String METADATA_ALLERGIES_DETAILS = "allergies_details";

	public static final String METADATA_LAST_CARE_PROVIDER = "last_care_provider";
	public static final String METADATA_CAN_CONTACT_LAST_PROVIDER = "can_contact_last_provider";
	public static final String METADATA_OTHER_INFORMATION = "other_information";
	public static final String METADATA_CAN_DISPLAY_PARENT_INFORMATION = "can_display_parent_information";

	public static final String METADATA_OTHER_CUSTODIAN = "other_custodian";
	public static final String METADATA_RELATIVE_1 = "relative_1";
	public static final String METADATA_RELATIVE_2 = "relative_2";
	public static final String METADATA_RELATION = "relation_";
	
	public Collection getSiblings() throws NoSiblingFound {
		try {
			return getFamilyLogic().getSiblingsFor(this);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}
	
	public Collection getCustodians() throws NoCustodianFound {
		try {
			return getFamilyLogic().getCustodiansFor(this);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}
	
	public Custodian getMother() {
		Custodian custodian = getCustodian(FamilyConstants.RELATION_MOTHER);
		if (custodian == null) {
			try {
				GenderHome home = (GenderHome) getIDOHome(Gender.class);
				return getCustodianByGender(home.getFemaleGender());
			}
			catch (FinderException fe) {
				log(fe);
			}
			catch (IDOLookupException ile) {
				throw new IBORuntimeException(ile);
			}
		}
		
		return custodian;
	}
	
	public Custodian getFather() {
		Custodian custodian = getCustodian(FamilyConstants.RELATION_FATHER);
		if (custodian == null) {
			try {
				GenderHome home = (GenderHome) getIDOHome(Gender.class);
				return getCustodianByGender(home.getMaleGender());
			}
			catch (FinderException fe) {
				log(fe);
			}
			catch (IDOLookupException ile) {
				throw new IBORuntimeException(ile);
			}
		}
		
		return custodian;
	}
	
	public Custodian getCustodian(String relation) {
		Collection metadataValues = getMetaDataAttributes().values();
		if (metadataValues != null) {
			Iterator iter = metadataValues.iterator();
			while (iter.hasNext()) {
				Object value = iter.next();
				if (value.equals(relation)) {
					try {
						return getCustodianByPrimaryKey(value);
					}
					catch (FinderException fe) {
						log(fe);
					}
				}
			}
		}
		
		return null;
	}
	
	public void setRelation(Custodian custodian, String relation) {
		setMetaData(METADATA_RELATION + custodian.getPrimaryKey().toString(), relation, "java.lang.String");
	}
	
	public String getRelation(Custodian custodian) {
		return getMetaData(METADATA_RELATION + custodian.getPrimaryKey().toString());
	}

	public Custodian getExtraCustodian() {
		String custodianPK = this.getMetaData(METADATA_OTHER_CUSTODIAN);
		if (custodianPK != null) {
		}
		return null;
	}
	
	public void setExtraCustodian(Custodian custodian) {
		setExtraCustodian(custodian, null);
	}
	
	public void setExtraCustodian(Custodian custodian, String relation) {
		setMetaData(METADATA_OTHER_CUSTODIAN, custodian.getPrimaryKey().toString(), "com.idega.user.data.User");
		if (relation != null && relation.length() > 0) {
			setRelation(custodian, relation);
		}
	}
	
	public List getRelatives() {
		List relatives = new ArrayList();

		for (int a = 1; a <= 2; a++) {
			String name = getMetaData((a == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_name");
			String relation = getMetaData((a == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_relation");
			String homePhone = getMetaData((a == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_homePhone");
			String workPhone = 	getMetaData((a == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_workPhone");
			String mobilePhone = getMetaData((a == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_mobilePhone");
			String email = getMetaData((a == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_email");
			
			if (name != null) {
				Relative relative = new Relative();
				relative.setName(name);
				relative.setRelation(relation);
				relative.setHomePhone(homePhone);
				relative.setWorkPhone(workPhone);
				relative.setMobilePhone(mobilePhone);
				relative.setEmail(email);
				
				relatives.add(relative);
			}
		}
		
		return relatives;
	}
	
	public void storeRelative(String name, String relation, int number, String homePhone, String workPhone, String mobilePhone, String email) {
		if (number > 2 || number < 1) {
			return;
		}
		
		setMetaData((number == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_name", name, "java.lang.String");
		setMetaData((number == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_relation", relation, "java.lang.String");
		setMetaData((number == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_homePhone", homePhone, "java.lang.String");
		setMetaData((number == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_workPhone", workPhone, "java.lang.String");
		setMetaData((number == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_mobilePhone", mobilePhone, "java.lang.String");
		setMetaData((number == 1 ? METADATA_RELATIVE_1 : METADATA_RELATIVE_2) + "_email", email, "java.lang.String");
	}
	
	public Boolean hasGrowthDeviation() {
		String meta = getMetaData(METADATA_GROWTH_DEVIATION);
		if (meta != null && meta.length() > 0) {
			return new Boolean(meta);
		}
		return null;
	}
	
	public void setHasGrowthDeviation(Boolean hasGrowthDeviation) {
		if (hasGrowthDeviation != null) {
			setMetaData(METADATA_GROWTH_DEVIATION, hasGrowthDeviation.toString());
		}
		else {
			removeMetaData(METADATA_GROWTH_DEVIATION);
		}
	}

	public String getGrowthDeviationDetails() {
		return getMetaData(METADATA_GROWTH_DEVIATION_DETAILS);
	}
	
	public void setGrowthDeviationDetails(String details) {
		if (details != null && details.length() > 0) {
			setMetaData(METADATA_GROWTH_DEVIATION_DETAILS, details);
		}
		else {
			removeMetaData(METADATA_GROWTH_DEVIATION_DETAILS);
		}
	}

	public Boolean hasAllergies() {
		String meta = getMetaData(METADATA_ALLERGIES);
		if (meta != null && meta.length() > 0) {
			return new Boolean(meta);
		}
		return null;
	}
	
	public void setHasAllergies(Boolean hasAllergies) {
		if (hasAllergies != null) {
			setMetaData(METADATA_ALLERGIES, hasAllergies.toString());
		}
		else {
			removeMetaData(METADATA_ALLERGIES);
		}
	}

	public String getAllergiesDetails() {
		return getMetaData(METADATA_ALLERGIES_DETAILS);
	}
	
	public void setAllergiesDetails(String details) {
		if (details != null && details.length() > 0) {
			setMetaData(METADATA_ALLERGIES_DETAILS, details);
		}
		else {
			removeMetaData(METADATA_ALLERGIES_DETAILS);
		}
	}
	
	private Custodian getCustodianByPrimaryKey(Object primaryKey) throws FinderException {
		try {
			CustodianHome home = (CustodianHome) getIDOHome(Custodian.class);
			return home.findByPrimaryKey(new Integer(primaryKey.toString()));
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private Custodian getCustodianByGender(Gender gender) {
		try {
			Collection custodians = getCustodians();
			Iterator iter = custodians.iterator();
			while (iter.hasNext()) {
				Custodian custodian = (Custodian) iter.next();
				if (custodian.getGender().equals(gender)) {
					return custodian;
				}
			}
		}
		catch (NoCustodianFound ncf) {
			//No custodians found;
		}
		
		Custodian extraCustodian = getExtraCustodian();
		if (extraCustodian != null && extraCustodian.getGender().equals(gender)) {
			return extraCustodian;
		}
		
		return null;
	}
	
	private FamilyLogic getFamilyLogic() {
		try {
			return (FamilyLogic) IBOLookup.getServiceInstance(getIWMainApplication().getIWApplicationContext(), FamilyLogic.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	public Integer ejbFindUserForUserGroup(Group group) throws FinderException {
		return super.ejbFindUserForUserGroup(group);
	}
}