package io;

public class DataInput {

    private InputType type;
    private String data;

    public DataInput() {
    }

    public DataInput(InputType type, String data) {
        this.type = type;
        this.data = data;
    }

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataInput dataInput = (DataInput) o;

        if (getType() != dataInput.getType()) return false;
        return getData().equals(dataInput.getData());
    }

}
