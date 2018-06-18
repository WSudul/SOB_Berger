package report;

import berger.BergerCode;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.ChangeType;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Record {

    private final BergerCode modifiedBergerCode; //contains modified instance of data and check bits
    private final ChangeType changeType;
    private final boolean isErrorDetected;

    public Record(BergerCode modifiedBergerCode, ChangeType changeType) {
        this.modifiedBergerCode = modifiedBergerCode;
        this.changeType = changeType;
        this.isErrorDetected = modifiedBergerCode.isErrorDetected();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Record{");
        sb.append("modifiedBergerCode=").append(modifiedBergerCode);
        sb.append(", changeType=").append(changeType);
        sb.append(", isErrorDetected=").append(isErrorDetected);
        sb.append('}');
        return sb.toString();
    }
}
