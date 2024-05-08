package com.lovemarker.domain.memory;

import com.lovemarker.domain.common.BaseTimeEntity;
import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.memory.vo.AddressInfo;
import com.lovemarker.domain.memory.vo.MemoryContent;
import com.lovemarker.domain.memory.vo.MemoryTitle;
import com.lovemarker.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "memory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memory extends BaseTimeEntity {

    @Id
    @Column(name = "memory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoryId;

    @Embedded
    private MemoryTitle title;

    @Embedded
    private MemoryContent content;

    @Embedded
    private AddressInfo addressInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "memory", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemoryImage> images = new ArrayList<>();

    public Memory(String title, String content, String address, Point point, Couple couple, User user, List<String> images) {
        this.title = new MemoryTitle(title);
        this.content = new MemoryContent(content);
        this.addressInfo = new AddressInfo(address, point);
        this.couple = couple;
        this.user = user;
        if (Objects.nonNull(images)) {
            List<MemoryImage> newImages = images.stream()
                .map(url -> new MemoryImage(url, this))
                .toList();
            this.images.addAll(newImages);
        }
    }
}