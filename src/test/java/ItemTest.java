import com.ad.Item;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ItemTest {

    @Test
    public void itemsHaveCorrectPrice(){
        assertEquals(Item.Apple.getPrice(), BigDecimal.valueOf(0.60));
        assertEquals(Item.Banana.getPrice(), BigDecimal.valueOf(0.30));
    }



}
