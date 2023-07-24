package com.projectbarber.domain.barber;

import com.projectbarber.domain.barber.dto.CreateBarberDTO;
import com.projectbarber.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "Barber")
@Entity(name = "Barber")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idBarbeiro", callSuper = false)
public class Barber extends User{

    public Barber(@Valid CreateBarberDTO dados) {
        this.cpf = dados.getCpf();
        this.active = dados.getActive();
    }

    public Barber(@Valid CreateBarberDTO dados, User user) {
        this.user = user;
        this.cpf = dados.getCpf();
        this.active = dados.getActive();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Barber")
    private Long idBarbeiro;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "ID_User")
    private  User user;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "CPF")
    private String cpf;

    
}
