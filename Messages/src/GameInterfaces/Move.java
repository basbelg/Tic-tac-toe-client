package GameInterfaces;

import java.io.Serializable;

public interface Move extends Serializable {
    int getPlayer();
    int getRow();
    int getColumn();
}
