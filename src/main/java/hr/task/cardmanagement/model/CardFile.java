package hr.task.cardmanagement.model;

import hr.task.cardmanagement.enums.CardFileStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("CARD_FILE")
public class CardFile {

    @Id
    @Column("ID")
    private Long id;

    @Column("NAME")
    private String name;

    @Column("OIB")
    private String oib;

    @Column("STATUS")
    private CardFileStatus status;

    @Column("MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Column("INSERTED_AT")
    private LocalDateTime insertedAt = LocalDateTime.now();

    public CardFile() {
    }

    public CardFile(String name, String oib, CardFileStatus status) {
        this.name = name;
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

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public CardFileStatus getStatus() {
        return status;
    }

    public void setStatus(CardFileStatus status) {
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
        return "CardFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", oib='" + oib + '\'' +
                ", status=" + status +
                ", modifiedAt=" + modifiedAt +
                ", insertedAt=" + insertedAt +
                '}';
    }
}
