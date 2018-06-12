package report;

import berger.BergerCode;
import io.ChangeType;

import java.time.Instant;

public class Record {
    private final Instant timestamp;
    private final BergerCode modifiedBergerCode; //contains modified instance of data and check bits
    private final ChangeType changeType;

    public Record(BergerCode modifiedBergerCode, ChangeType changeType) {
        timestamp = Instant.now();
        this.modifiedBergerCode = modifiedBergerCode;
        this.changeType = changeType;
    }

    public boolean isErrorDetected() {
        return modifiedBergerCode.isErrorDetected();
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Record{");

        sb.append("\ttimestamp=").append(timestamp);
        sb.append("\n\tmodifiedBergerCode=").append(modifiedBergerCode.toString());
        sb.append("\n\tchangeType=").append(changeType);
        sb.append("\n\tisErrorDetected=").append(isErrorDetected());
        sb.append("\n}");
        return sb.toString();
    }
}
