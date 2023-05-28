package com.tutorial.eventlisterner;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class UpdatedAtListener {

    /**
     * Entity Listener
     * ● Saat kita memanipulasi data Entity, kadang kita ingin melakukan instruksi program yang sama
     * ● Misal, setiap data diubah, kita ingin mengubah kolom updated_at menjadi waktu saat ini misalnya
     * ● Jika dilakukan di semua kode program kita, maka akan menyulitkan
     * ● Untungnya JPA memiliki fitur Entity Listener, dimana kita bisa membuat sebuah class Listener,
     *   yang nanti akan dipanggil oleh JPA ketika sebuah operasi terjadi terhadap Entity nya
     *
     * Event Annotation Keterangan
     * @PrePersist      Dieksekusi sebelum melakukan persist entity
     * @PostPersist     Dieksekusi setelah melakukan persist entity
     * @PreRemove       Dieksekusi sebelum melakukan remove entity
     * @PostRemove      Dieksekusi setelah melakukan remove entity
     * @PreUpdate       Dieksekusi sebelum melakukan update entity
     * @PostUpdate      Dieksekusi setelah melakukan update entity
     * @PostLoad        Dieksekusi setelah melakukan load entity
     *
     */

    @PrePersist
    @PreUpdate
    public void setLastUpdatedAt(UpdatedAtAware object){
        object.setUpdatedAt(LocalDateTime.now()); // static LocalDateTime now() // mendapatkan waktu saat ini sesuai ZoneID
    }

}
