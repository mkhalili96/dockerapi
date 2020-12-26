package net.seensin.springdockerswarmmanagementapi.modules.ssh.to;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleTask implements Serializable {
    private OidExtension target;
    private String value;
    private TaskType taskType;
    SimpleTaskEnum taskName;


    public SimpleTask() {
    }

    public enum TaskType {SIMPLE_READ, SIMPLE_WRITE, PROCESSABLE}

    public enum SimpleTaskEnum implements Serializable {

        //Shelf
        NULL("null", HwLevel.SLOT);

        private final String oidName;
        private final HwLevel hwLevel;

        SimpleTaskEnum(String _oidName, HwLevel _hwLevel) {

            oidName = _oidName;
            hwLevel = _hwLevel;
        }

        public String getOidName() {
            return oidName;
        }

        public HwLevel getHwLevel() {
            return hwLevel;
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
}
