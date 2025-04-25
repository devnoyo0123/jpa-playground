// 1. Tag 엔티티 (Many-to-Many 관계의 한쪽)
package org.example.jpaplayground.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    // 관계 매핑(Many-to-Many의 분리)
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTag> accountTags = new ArrayList<>();

    @Builder
    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // 연관관계 편의 메서드
    public void addAccountTag(AccountTag accountTag) {
        this.accountTags.add(accountTag);
    }

    public void removeAccountTag(AccountTag accountTag) {
        this.accountTags.remove(accountTag);
    }

    // Tag 업데이트 메서드
    public void updateName(String name) {
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
