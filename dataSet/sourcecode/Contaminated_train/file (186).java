package org.amuji.jpa.domain.model;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * @author Roy Yang
 *         11/18/2014.
 */
@MappedSuperclass
public abstract class AbstractRefData extends IdEntity {
    @Column(length = DBSchemaConsts.LEN_CODE)
    private String code;
    @Column(length = DBSchemaConsts.LEN_NAME)
    private String name;
    @Lob
    @Column(length = DBSchemaConsts.LEN_DESC)
    private String description;

    protected AbstractRefData() {
        this(null, null, null);
    }

    public AbstractRefData(String code, String name) {
        this(code, name, null);
    }

    public AbstractRefData(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractRefData that = (AbstractRefData) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
