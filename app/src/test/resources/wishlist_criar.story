Narrative: Precisamos disponibilizar um serviço ao qual o cliente possa adicionar, remover ou consultar produtos na sua
lista de favoritos, também caso necessário deve ser possível consultar se um determinado produto existe na lista de
favoritos do cliente

Scenario: Cliente adiciona um produto a sua lista de favoritos
  Given um cliente adiciona um produto
  When quando for adicionar um produto
  Then deve salvar na lista de produtos favoritos se não houver mais de 20 produtos na lista

Scenario: Cliente adiciona um produto a sua lista de favoritos que contém 20 produtos
  Given um cliente adiciona um produto
  When quando for adicionar um novo produto
  Then deve salvar na lista de produtos favoritos e remover o produto mais antigo existente na lista