package is.idega.block.family.business;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import com.idega.core.localisation.data.ICLanguage;
import com.idega.core.location.data.Country;
import com.idega.core.location.data.PostalCode;
import com.idega.core.user.data.User;
import com.idega.util.CoreConstants;

/**
 * Spring bean to provide services from {@link FamilyLogic}
 * 
 * @author <a href="mailto:valdas@idega.com">Valdas Žemaitis</a>
 * @version $Revision: 1.0 $
 * 
 *          Last modified: $Date: 2009.09.29 16:25:26 $ by: $Author: valdas $
 */
public interface FamilyHelper {

	public static final String BEAN_IDENTIFIER = "familyLogicServicesProvider";

	public Map<Locale, Map<String, String>> getTeenagesOfCurrentUser();

	public String getSpouseName();

	public String getSpousePersonalId();

	public String getSpouseTelephone();

	public String getSpouseMobile();

	public String getSpouseEmail();

	/**
	 * <p>
	 * Method for getting name of {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link User#getName()}, {@link CoreConstants#EMPTY} if does not
	 *         exist.
	 */
	public String getName(String userId);

	/**
	 * <p>
	 * Method for getting social security code {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link User#getPersonalID()}, {@link CoreConstants#EMPTY} if does
	 *         not exist.
	 */
	public String getSocialSecurityCode(String userId);

	/**
	 * <p>
	 * Method for getting main address of {@link User}. Address is made of room
	 * number, house number, street, city or village, region, country.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return Address of child of current user, {@link CoreConstants#EMPTY} if
	 *         does not exist.
	 */
	public String getAddress(String userId);

	/**
	 * <p>
	 * Method for getting postal code of child of {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link PostalCode#getPostalCode} of user,
	 *         {@link CoreConstants#EMPTY} if do not exist.
	 */
	public String getPostalCode(String userId);

	/**
	 * <p>
	 * Method for getting main phone number of {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link com.idega.user.data.User#getUsersHomePhone().getNumber()},
	 *         {@link CoreConstants#EMPTY} if do not exist.
	 */
	public String getHomePhoneNumber(String userId);

	/**
	 * <p>
	 * Method for getting cell phone number of {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link com.idega.user.data.User#getUsersMobilePhone()
	 *         .getNumber()}, {@link CoreConstants#EMPTY} if do not exist.
	 */
	public String getCellPhoneNumber(String userId);

	/**
	 * <p>
	 * Method for getting work phone number of {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link com.idega.user.data.User#getUsersWorkPhone().getNumber()},
	 *         {@link CoreConstants#EMPTY} if do not exist.
	 */
	public String getWorkPhoneNumber(String userId);

	/**
	 * <p>
	 * Method for getting e-mail address of {@link User}.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link com.idega.user.data.User#getUsersEmail()
	 *         #getEmailAddress()}, {@link CoreConstants#EMPTY} if do not exist.
	 */
	public String getEMailAddress(String userId);

	/**
	 * <p>
	 * Method for getting relations from {@link FamilyConstants}.
	 * </p>
	 * 
	 * @return Possible relations from {@link FamilyConstants} or
	 *         {@link Collections#emptyMap()} if does not exist.
	 */
	public Map<Locale, Map<String, String>> getRelationNames();

	/**
	 * <p>
	 * Method, for getting relation type from {@link FamilyConstants}.
	 * </p>
	 * 
	 * @param userId
	 *            Child, smaller brother or other user
	 *            {@link com.idega.user.data.User#getId()}.
	 * @param relatedUserId
	 *            Parent, sibling etc. {@link com.idega.user.data.User#getId()}.
	 * @return Relation from {@link FamilyConstants}, -1 if not found.
	 */
	public String getRelation(String userId, String relatedUserId);

	/**
	 * <p>
	 * Method for getting all languages exits in database.
	 * </p>
	 * 
	 * @return Map of languages, or {@link Collections#emptyMap()} if does not
	 *         exist.
	 */
	public Map<Locale, Map<String, String>> getLanguages();

	/**
	 * <p>
	 * Method for getting {@link String} representation of
	 * {@link User#getLanguages()}.
	 * </p>
	 * 
	 * @param numberOfLanguage
	 *            Language number in {@link User#getLanguages()}.
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return Language from user info, {@link CoreConstants#EMPTY} if does not
	 *         exist.
	 */
	public String getLanguage(int numberOfLanguage, String userId);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link ICLanguage} from {@link User#getPreferredLocale} or first
	 *         {@link ICLanguage} from
	 *         {@link FamilyHelper#getLanguage(int, String)}.
	 *         {@link CoreConstants#EMPTY}, if no preferred {@link Locale} or
	 *         {@link ICLanguage} was found.
	 */
	public String getMotherLanguage(String userId);

	/**
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @see is.idega.block.family.business.FamilyHelper#getLanguage(int, String)
	 * @return Language from {@link FamilyHelper#getLanguage(int, String)} with
	 *         number 1, {@link CoreConstants#EMPTY} if country does not found.
	 */
	public String getSecondLanguage(String userId);

	/**
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @see is.idega.block.family.business.FamilyHelper#getLanguage(int, String)
	 * @return Language from {@link FamilyHelper#getLanguage(int, String)} with
	 *         number 2, {@link CoreConstants#EMPTY} if country does not found.
	 */
	public String getThirdLanguage(String userId);

	/**
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @see is.idega.block.family.business.FamilyHelper#getLanguage(int, String)
	 * @return Language from {@link FamilyHelper#getLanguage(int, String)}. with
	 *         number 3, {@link CoreConstants#EMPTY} if country does not found.
	 */
	public String getFourthLanguage(String userId);

	/**
	 * <p>
	 * Find a country, where user lives in.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return {@link Country#getIsoAbbreviation()} or
	 *         {@link CoreConstants#EMPTY} if country does not found.
	 */
	public String getCountry(String userId);

	/**
	 * <p>
	 * Method for getting all countries, which exits in database.
	 * </p>
	 * 
	 * @return Map of countries, or empty Map if does not exist.
	 */
	public Map<Locale, Map<String, String>> getCountries();

	/**
	 * <p>
	 * Method for getting marital status.
	 * </p>
	 * 
	 * @param userId
	 *            Id of user {@link com.idega.user.data.User#getId()}.
	 * @return Possible marital statuses are detailed at {@link FamilyConstants}
	 *         or {@link CoreConstants#EMPTY} if not found.
	 */
	public String getMaritalStatus(String userId);

	/**
	 * <p>
	 * Method for getting id of parent, who is connected now or is parent.
	 * </p>
	 * 
	 * @param childId
	 *            Id of child user {@link com.idega.user.data.User#getId()}.
	 * @return Id of father, mother or somebody, who is the parent of child:
	 *         {@link com.idega.user.data.User#getId()}.
	 */
	public String getCurrentParent(String childId);

	/**
	 * <p>
	 * Method for getting id of another parent. Not the one, who is connected
	 * now, or already been chosen.
	 * </p>
	 * 
	 * @param childId
	 *            Id of child user {@link com.idega.user.data.User#getId()}.
	 * @return Id of father, mother or somebody, who is the parent of child:
	 *         {@link com.idega.user.data.User#getId()}.
	 */
	public String getAnotherParent(String childId);

	public String getMinusOne();
}
