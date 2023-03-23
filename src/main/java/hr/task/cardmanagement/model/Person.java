package hr.task.cardmanagement.model;

import hr.task.cardmanagement.enums.CardStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("PERSON")
public class Person {

    @Id
    @Column("ID")
    private Long id;

    @Column("NAME")
    private String name;

    @Column("SURNAME")
    private String surname;

    @Column("OIB")
    private String oib;

    @Column("STATUS")
    private CardStatus status;

    @Column("MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Column("INSERTED_AT")
    private LocalDateTime insertedAt = LocalDateTime.now();

    public Person() {
    }

    public Person(String name, String surname, String oib, CardStatus status) {

        this.name = name;
        this.surname = surname;
        this.oib = oib;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public LocalDateTime getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", oib='" + oib + '\'' +
                ", status=" + status +
                '}';
    }
}
