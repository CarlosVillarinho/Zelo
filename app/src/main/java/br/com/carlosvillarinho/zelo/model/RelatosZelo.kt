package br.com.carlosvillarinho.zelo.model

data class ExemploRelato(
    val title: String,
    val body: String,
    val apoios: Int
)

object RelatosZelo {

    val exemplos: List<ExemploRelato> = listOf(
        ExemploRelato(
            "Buraco grande na Rua das Acácias",
            "Buraco de cerca de 1 metro de largura no meio da pista, perto do número 120. Já danificou pneus e é perigoso para motos à noite. Local: Rua das Acácias, Jardim Primavera.",
            48
        ),
        ExemploRelato(
            "Postes apagados na Praça da Paz",
            "Três postes da praça estão sem luz há semanas. O local fica escuro à noite e os moradores evitam passar por ali. Local: Praça da Paz, Vila Nova Esperança.",
            36
        ),
        ExemploRelato(
            "Entulho ocupando a calçada",
            "Restos de obra e móveis velhos tomam toda a calçada e obrigam os pedestres a andar pela rua. Local: Av. Brasil com Rua do Ipê, Centro.",
            21
        ),
        ExemploRelato(
            "Bueiro entupido causa alagamento",
            "A cada chuva forte a água sobe e invade a calçada e as garagens. O bueiro está cheio de lixo e folhas. Local: Rua dos Girassóis, 45, Jardim das Flores.",
            59
        ),
        ExemploRelato(
            "Árvore caída bloqueando a ciclovia",
            "Depois do temporal, uma árvore tombou sobre a ciclovia e ainda não foi retirada, o que força os ciclistas a ir para a pista. Local: Av. das Palmeiras, altura do 800, Parque Real.",
            17
        ),
        ExemploRelato(
            "Calçada quebrada em frente à escola",
            "Pisos soltos e buracos dificultam a passagem de crianças, idosos e cadeirantes. Local: Rua Esperança, 300, em frente à escola municipal, Bairro Aurora.",
            44
        ),
        ExemploRelato(
            "Vazamento de água na rua",
            "Há um vazamento constante saindo do asfalto, formando poça e desperdiçando água há mais de uma semana. Local: Rua Santa Clara, 78, Vila Santa Clara.",
            27
        ),
        ExemploRelato(
            "Semáforo apagado no cruzamento",
            "O semáforo está desligado e o cruzamento ficou perigoso nos horários de pico, com risco de acidentes. Local: Av. Independência com Rua Pinheiros, Vila Operária.",
            63
        ),
        ExemploRelato(
            "Terreno com mato alto e lixo",
            "O mato alto e o lixo acumulado no terreno baldio atraem animais peçonhentos e mosquitos. Local: Rua das Margaridas, 210, Jardim Primavera.",
            31
        ),
        ExemploRelato(
            "Ponto de ônibus danificado",
            "O abrigo está sem cobertura e com o banco quebrado, e os passageiros ficam expostos ao sol e à chuva. Local: Av. Brasil, em frente ao mercado, Centro.",
            19
        ),
        ExemploRelato(
            "Esgoto a céu aberto",
            "O esgoto escorre pela rua, causa mau cheiro e oferece risco à saúde das famílias da região. Local: Travessa dos Lírios, Vila Nova Esperança.",
            72
        ),
        ExemploRelato(
            "Faixa de pedestres apagada",
            "A faixa em frente à padaria desapareceu com o tempo e os motoristas não respeitam a travessia. Local: Rua Esperança com Rua do Comércio, Bairro Aurora.",
            26
        ),
        ExemploRelato(
            "Descarte irregular de lixo na esquina",
            "Sacos de lixo e colchões são deixados na esquina, atraindo ratos e insetos. Local: Rua do Ipê com Rua Verde, Centro.",
            38
        ),
        ExemploRelato(
            "Fios soltos em poste",
            "Fios de energia estão pendurados e muito baixos, quase encostando na calçada, com risco de choque. Local: Rua dos Coqueiros, 15, Parque Real.",
            54
        ),
        ExemploRelato(
            "Praça abandonada e pichada",
            "Brinquedos quebrados, bancos danificados e pichações tomaram a praça, que as crianças deixaram de usar. Local: Praça das Crianças, Jardim das Flores.",
            33
        )
    )
}