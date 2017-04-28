/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.main.java.com.djrapitops.plan.data.cache;

import java.util.UUID;
import main.java.com.djrapitops.plan.Plan;
import main.java.com.djrapitops.plan.data.DemographicsData;
import main.java.com.djrapitops.plan.data.SessionData;
import main.java.com.djrapitops.plan.data.UserData;
import main.java.com.djrapitops.plan.data.cache.SessionCache;
import org.bukkit.plugin.java.JavaPlugin;
import org.easymock.EasyMock;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import test.java.utils.MockUtils;
import test.java.utils.TestInit;

/**
 *
 * @author Rsl1122
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaPlugin.class)
public class SessionCacheTest {

    private SessionCache test;

    /**
     *
     */
    public SessionCacheTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        test = new SessionCache();        
    }

    /**
     *
     */
    @Test
    public void testStartSession() {
        UUID uuid = MockUtils.getPlayerUUID();
        test.startSession(uuid);
        assertTrue("Didn't contain new session", test.getActiveSessions().containsKey(uuid));
    }

    /**
     *
     */
    @Test
    public void testEndSession() {
        UUID uuid = MockUtils.getPlayerUUID();
        test.getActiveSessions().put(uuid, new SessionData(0));
        test.endSession(uuid);
        SessionData testSession = test.getActiveSessions().get(uuid);
        assertTrue("Didn't end session", testSession.getSessionEnd() != -1);
        assertTrue("Session length not positive", testSession.getLength() > 0L);
        assertTrue("Session not valid", testSession.isValid());
    }

    /**
     *
     */
    @Test
    public void testAddSession() {
        UUID uuid = MockUtils.getPlayerUUID();
        TestInit t = new TestInit();
        assertTrue("Not set up", t.setUp());
        Plan plan = t.getPlanMock();
        PowerMock.mockStatic(JavaPlugin.class);
        EasyMock.expect(JavaPlugin.getPlugin(Plan.class)).andReturn(plan);
        EasyMock.expect(JavaPlugin.getPlugin(Plan.class)).andReturn(plan);
        EasyMock.expect(JavaPlugin.getPlugin(Plan.class)).andReturn(plan);
        PowerMock.replay(JavaPlugin.class);
        test.getActiveSessions().put(uuid, new SessionData(0));
        test.endSession(uuid);
        UserData data = new UserData(MockUtils.mockPlayer(), new DemographicsData());
        test.addSession(data);
        assertTrue("Didn't add session to data", data.getSessions().size() == 1);
    }

}
