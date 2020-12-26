package net.seensin.springdockerswarmmanagementapi.modules.ssh.to;

import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.LevelMismatchException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.OidExtensionFormatException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OidExtension implements Serializable {

    protected Integer node = null;

    protected Integer rack = null;

    protected Integer shelf = null;

    protected Integer slot = null;

    protected Integer module = null;

    private HwLevel level;

    public OidExtension(Integer node, Integer rack, Integer shelf, Integer slot, Integer module) {
        this.node = node;
        this.rack = rack;
        this.shelf = shelf;
        this.slot = slot;
        this.module = module;
        level = HwLevel.MODULE;
    }

    public OidExtension(Integer node, Integer rack, Integer shelf, Integer slot) {
        this.node = node;
        this.rack = rack;
        this.shelf = shelf;
        this.slot = slot;
        level = HwLevel.SLOT;

    }

    public OidExtension(Integer node, Integer rack, Integer shelf) {
        this.node = node;
        this.rack = rack;
        this.shelf = shelf;
        level = HwLevel.SHELF;

    }

    public OidExtension(Integer node, Integer rack) {
        this.node = node;
        this.rack = rack;
        level = HwLevel.RACK;

    }

    public OidExtension(Integer node) {
        this.node = node;
        level = HwLevel.NODE;

    }


    protected OidExtension() {

    }

    public static OidExtension OidExtensionByString(String OidExtensionString) throws OidExtensionFormatException {

        OidExtension oidExtension = null;
        List<Integer> result = new ArrayList<>();
        for (String x : OidExtensionString.split("\\.")) {
            try {
                result.add(Integer.parseInt(x));
            }catch (Exception e){
                throw new OidExtensionFormatException();
            }
        }

        switch (result.size()) {
            case 1:
                oidExtension = new OidExtension(result.get(0));
                break;
            case 2:
                oidExtension = new OidExtension(result.get(0), result.get(1));

                break;
            case 3:
                oidExtension = new OidExtension(result.get(0), result.get(1), result.get(2));

                break;
            case 4:
                oidExtension = new OidExtension(result.get(0), result.get(1), result.get(2), result.get(3));

                break;
            case 5:
                oidExtension = new OidExtension(result.get(0), result.get(1), result.get(2), result.get(3), result.get(4));

                break;
        }

        return oidExtension;

    }

    public Integer getNode() {
        return node;
    }

    public Integer getRack() {
        return rack;
    }

    public Integer getShelf() {
        return shelf;
    }

    public Integer getSlot() {
        return slot;
    }

    public Integer getModule() {
        return module;
    }

    public HwLevel getLevel() {
        if (this.level == null) {
            try {
                levelGenerator();
            } catch (LevelMismatchException e) {

            }
        }
        return level;
    }

    @Override
    public String toString() {
        return node.toString() + (rack != null ? ('.' + rack.toString() + (shelf != null ? ('.' + shelf.toString() + (slot != null ? ('.' + slot.toString() + (module != null ? '.' + module.toString() : "")) : "")) : "")) : "");
    }

    public String toString(HwLevel requestedLevel) throws Exception {
        if (this.level == null)
            levelGenerator();

        if (requestedLevel.getLevelValue() > level.getLevelValue()) {
            throw new LevelMismatchException();
        }

        return node.toString() + ((rack != null && requestedLevel.getLevelValue() >= HwLevel.RACK.getLevelValue()) ? '.' + rack.toString() + ((shelf != null && requestedLevel.getLevelValue() >= HwLevel.SHELF.getLevelValue()) ? ('.' + shelf.toString() + ((slot != null && requestedLevel.getLevelValue() >= HwLevel.SLOT.getLevelValue()) ? ('.' + slot.toString() + ((module != null && requestedLevel.getLevelValue() >= HwLevel.MODULE.getLevelValue()) ? '.' + module.toString() : "")) : "")) : "") : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OidExtension)) return false;
        OidExtension that = (OidExtension) o;
        return node.equals(that.node) &&
                Objects.equals(rack, that.rack) &&
                Objects.equals(shelf, that.shelf) &&
                Objects.equals(slot, that.slot) &&
                Objects.equals(module, that.module) &&
                level == that.level;
    }

    public Boolean equalsInNodeLevel(OidExtension oidExtension) {
        return this.getNode().equals(oidExtension.getNode());
    }

    public Boolean equalsInRackLevel(OidExtension oidExtension) {
        return equalsInNodeLevel(oidExtension) && this.getRack().equals(oidExtension.getRack());
    }

    public Boolean equalsInShelfLevel(OidExtension oidExtension) {
        return equalsInRackLevel(oidExtension) && this.getShelf().equals(oidExtension.getShelf());
    }

    public Boolean equalsInSlotLevel(OidExtension oidExtension) {
        return equalsInShelfLevel(oidExtension) && this.getSlot().equals(oidExtension.getSlot());
    }


    public Boolean equalsInModuleLevel(OidExtension oidExtension) {
        return equalsInSlotLevel(oidExtension) && this.getModule().equals(oidExtension.getModule());
    }

    public OidExtension Trim(HwLevel hwLevel) throws LevelMismatchException {
        if (this.level == null)
            levelGenerator();

        if (hwLevel.getLevelValue() >= this.level.getLevelValue()) {
            throw new LevelMismatchException();
        }

        OidExtension oidExtension = null;


        switch (hwLevel.getLevelValue()) {
            case 1:
                oidExtension = new OidExtension(node);
                break;
            case 2:
                oidExtension = new OidExtension(node, rack);

                break;
            case 3:
                oidExtension = new OidExtension(node, rack, shelf);

                break;
            case 4:
                oidExtension = new OidExtension(node, rack, shelf, slot);

                break;
            case 5:
                oidExtension = new OidExtension(node, rack, shelf, slot, module);

                break;
        }

        return oidExtension;


    }


    @Override
    public int hashCode() {
        return Objects.hash(node, rack, shelf, slot, module, level);

    }
     private void levelGenerator() throws LevelMismatchException {
        if (this.node == null)
            // todo : handle exception with correct error
            throw new LevelMismatchException();
        else if (this.rack == null)
            this.level = HwLevel.NODE;
        else if (this.shelf == null)
            this.level = HwLevel.RACK;
        else if (this.slot == null)
            this.level = HwLevel.SHELF;
        else if (this.module == null)
            this.level = HwLevel.SLOT;
        else
            this.level = HwLevel.MODULE;
     }
    public enum HwLevel {
        NODE(1),
        RACK(2),
        SHELF(3),
        SLOT(4),
        MODULE(5);

        private final int LevelValue;

        HwLevel(int level) {
            LevelValue = level;
        }

        public int getLevelValue() {
            return LevelValue;
        }
    }
}
