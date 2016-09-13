package ua.nure.efimov.summarytask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.nure.efimov.summarytask4.db.mysql.MySqlDaoTest;
import ua.nure.efimov.summarytask4.services.AnswersCheckServiceTest;
import ua.nure.efimov.summarytask4.services.RegistrationServiceTest;
import ua.nure.efimov.summarytask4.utils.CommonUtilsTest;
import ua.nure.efimov.summarytask4.utils.LogUtilsTest;

@RunWith(Suite.class)
@SuiteClasses({ MySqlDaoTest.class, CommonUtilsTest.class, LogUtilsTest.class, AnswersCheckServiceTest.class,
		RegistrationServiceTest.class })
public class AllTests {

}
