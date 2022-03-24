package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    public Person person;
    public String quote;

    @SuppressWarnings("unused")
    private Quote() {
        // for object mappers
    }

    public Quote(Person person, String quote) {
        this.person = person;
        this.quote = quote;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}