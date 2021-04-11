package ucc.utils;
import java.util.Map;
import net.serenitybdd.core.pages.WebElementFacade;
import ucc.pages.ui.CommonFunc;


public class CssUtils {

    TestUtils tUtil = new TestUtils();
    JsonUtils jsonUtils = new JsonUtils();
    CommonFunc commonFunc = new CommonFunc();

	private String getExpStyle(String file, String attrib, String styleType) {
		return jsonUtils.getFromJSON(file, "['" + attrib + "']['" + styleType + "']");
	}

	private boolean checkActualVsExpCssDesign(String file, WebElementFacade element, String Attrib, String cssStyle) {
		return commonFunc.getCssValue(element, cssStyle).equals(getExpStyle(file, Attrib, cssStyle)) ? true : false;
	}

	private boolean checkActualVsExpAttrib(Map<String, String> map, String file, WebElementFacade element,
			String Attrib, String cssStyle) {
		boolean val = commonFunc.getAttribute(element, cssStyle).equals(getExpStyle(file, Attrib, cssStyle)) ? true
				: false;
		map.put(Attrib, Boolean.toString(val));
		return val;
	}

	private boolean checkActualVsExpText(Map<String, String> map, String file, WebElementFacade element, String Attrib,
			String cssStyle) {
		boolean val = element.waitUntilVisible().getText().equals(getExpStyle(file, Attrib, cssStyle)) ? true : false;
		map.put(Attrib, Boolean.toString(val));
		return val;
	}

	/* cssValidator for different style combinations as per JSON */
	public boolean cssValidator(Map<String, String> map, String file, WebElementFacade element, String Attribute,
			String styleA, String styleB) {
		boolean val = checkActualVsExpCssDesign(file, element, Attribute, styleA)
				&& checkActualVsExpCssDesign(file, element, Attribute, styleB) ? true : false;
		map.put(Attribute, Boolean.toString(val));
		return val;
	}

	public boolean cssValidator(Map<String, String> map, String file, WebElementFacade element, String Attribute,
			String styleA, String styleB, String styleC) {
		boolean val = checkActualVsExpCssDesign(file, element, Attribute, styleA)
				&& checkActualVsExpCssDesign(file, element, Attribute, styleB)
				&& checkActualVsExpCssDesign(file, element, Attribute, styleC) ? true : false;
		map.put(Attribute, Boolean.toString(val));
		return val;
	}

	public boolean cssValidator(Map<String, String> map, String file, WebElementFacade element, String Attribute,
			String styleA, String styleB, String styleC, String styleD) {
		boolean val = checkActualVsExpCssDesign(file, element, Attribute, styleA)
				&& checkActualVsExpCssDesign(file, element, Attribute, styleB)
				&& checkActualVsExpCssDesign(file, element, Attribute, styleC)
				&& checkActualVsExpCssDesign(file, element, Attribute, styleD) ? true : false;
		map.put(Attribute, Boolean.toString(val));
		return val;
	}

	/* cssWithElementAttributeValidator for different styles */
	public boolean cssValidatorA(Map<String, String> map, String file, WebElementFacade element, String Attrib,
			String elementAttrib, String StyleA, String StyleB, String StyleC) {
		boolean val = cssValidator(map, file, element, Attrib, StyleA, StyleB, StyleC)
				&& checkActualVsExpAttrib(map, file, element, Attrib, elementAttrib) ? true : false;
		map.put(Attrib, Boolean.toString(val));
		return val;
	}

	public boolean cssValidatorA(Map<String, String> map, String file, WebElementFacade element, String Attrib,
			String elementAttrib, String StyleA, String StyleB, String StyleC, String StyleD) {
		boolean val = cssValidator(map, file, element, Attrib, StyleA, StyleB, StyleC)
				&& checkActualVsExpAttrib(map, file, element, Attrib, elementAttrib) ? true : false;
		map.put(Attrib, Boolean.toString(val));
		return val;
	}
	
	/* cssWithElementTextOrValueValidator for different styles */
	public boolean cssValidatorT(Map<String, String> map, String file, WebElementFacade element, String Attrib,
			String textOrVal, String StyleA, String StyleB, String StyleC) {
		boolean val = checkActualVsExpText(map, file, element, Attrib, textOrVal)
				&& cssValidator(map, file, element, Attrib, StyleA, StyleB, StyleC) ? true : false;
		map.put(Attrib, Boolean.toString(val));
		return val;
	}




}
