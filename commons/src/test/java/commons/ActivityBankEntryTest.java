package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActivityBankEntryTest {
    public ActivityBankEntry testEntity;
    public String id = "56_energy";
    public String imagePath = "/56/energy";
    public String title = "Activity title";
    public long consumptionWh = 56;
    public String source = "activity source";

    @BeforeEach
    public void setup() {
        testEntity = new ActivityBankEntry(id, imagePath,
                title, consumptionWh, source);
    }

    @Test
    public void constructorTest() {
        ActivityBankEntry toBeTested = new ActivityBankEntry(id, imagePath,
                title, consumptionWh, source);
        assertNotNull(toBeTested);
    }

    @Test
    public void getImagePathTest() {
        assertEquals(imagePath, testEntity.getImagePath());
    }

    @Test
    public void getIdTest() {
        assertEquals(id, testEntity.getId());
    }

    @Test
    public void getSourceTest() {
        assertEquals(source, testEntity.getSource());
    }

    @Test
    public void getConsumptionWh() {
        assertEquals(consumptionWh, testEntity.getConsumptionInWh());
    }

    @Test
    public void getTitleTest() {
        assertEquals(title, testEntity.getTitle());
    }
}
