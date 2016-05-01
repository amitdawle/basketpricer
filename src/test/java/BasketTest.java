import com.ad.Basket;
import com.ad.Item;
import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class BasketTest {

    @Test
    public void basketCanBeCorrectlySetup(){
        Basket b = new Basket();
        b.addItem(Item.Apple);
        b.addItem(Item.Apple);
        b.addItem(Item.Banana);

        assertThat(3 , is(b.getItems().size()));
        assertThat(2L , is(b.getItems().stream().filter(x -> x == Item.Apple ).count()));
        assertThat(1L , is(b.getItems().stream().filter(x -> x == Item.Banana ).count()));
    }

    @Test(expected = NullPointerException.class)
    public void basketCannotContainInvalidItems(){
        Basket b = new Basket();
        b.addItem(Item.Apple);
        b.addItem(Item.Apple);
        b.addItem(null);

        assertThat(3 , is(b.getItems().size()));
        assertThat(2L , is(b.getItems().stream().filter(x -> x == Item.Apple ).count()));
        assertThat(1L , is(b.getItems().stream().filter(x -> x == Item.Banana ).count()));
    }
}
