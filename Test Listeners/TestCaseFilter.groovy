import org.apache.commons.lang3.StringUtils

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable as GlobalVariable

class TestCaseFilter {
	
	private boolean allowAll;
	
	private String[] allowedTestCaseIds;
	
	public TestCaseFilter() {
		if (StringUtils.isNotBlank(GlobalVariable.testCaseFilter)) {
			allowAll = false;
			allowedTestCaseIds = GlobalVariable.testCaseFilter.split(",").collect({ "Test Cases/${it.trim()}"})
			KeywordUtil.logInfo("Only allow ${allowedTestCaseIds}")
		} else {
			allowAll = true;
			KeywordUtil.logInfo("Allow all test cases")
		}
	}
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		if (!allowAll) {
			String nextTestCaseId = testCaseContext.getTestCaseId()
			if (!allowedTestCaseIds.contains(nextTestCaseId)) {
				KeywordUtil.logInfo("Skip test case '${nextTestCaseId}'")
				testCaseContext.skipThisTestCase()
			}
		}
	}
}