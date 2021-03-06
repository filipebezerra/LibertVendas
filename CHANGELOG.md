Change Log
==========

Versão 1.0.9 (07/06/2017)
-----------------------
* **CORREÇÃO** Sincronização manual dos pedidos de novos clientes
* **CORREÇÃO** Consumo extremo de memória da visualização de gráfico dos pedidos
* **MELHORIA** Gráfico dos pedidos agora por padrão mostra pedidos do dia corrente
* **MELHORIA** Indicadores de status do pedido mais elegantes
* **MELHORIA** Teclado agora aparece escondido por padrão na digitação de pedidos e clientes
* **MELHORIA** Novo estilo de botões para incluir pedidos e clientes
* **CORREÇÃO** Filtro por data na visualização de gráfico dos pedidos

Versão 1.0.8 (22/05/2017)
-----------------------
* **CORREÇÃO** Cálculo base do valor de desconto

Versão 1.0.7 (22/05/2017)
-----------------------
* **CORREÇÃO** Migração do banco de dados após incluir o campo porcentagem desconto

Versão 1.0.6 (22/05/2017)
-----------------------
* **MELHORIA** Vendedor pode conceder porcentagem de desconto e visualizar o total do pedido considerando o valor do desconto ( [#94](https://github.com/filipebezerra/LibertVendas/issues/94) )
* **CORREÇÃO** Tratamento caso a tabela padrão cadastro da empresa esteja nulo  ( [#74](https://github.com/filipebezerra/LibertVendas/issues/74) )

Versão 1.0.5 (21/05/2017)
-----------------------
* **CORREÇÃO** Listagem de produtos não é recarregada após selecionar outro cliente ( [#91](https://github.com/filipebezerra/LibertVendas/issues/91) )
* **CORREÇÃO** Crash na validação de desconto caso a forma de pagamento não tenha desconto ( [#92](https://github.com/filipebezerra/LibertVendas/issues/92) )
* **CORREÇÃO** Não é possível aplicar desconto usando forma de pagamento que concede desconto ( [#93](https://github.com/filipebezerra/LibertVendas/issues/93) )

Versão 1.0.4 (20/05/2017)
-----------------------
* **CORREÇÃO** Totalização dos itens do pedido quando é usado o filtro do produto ( [#87](https://github.com/filipebezerra/LibertVendas/issues/87) )
* **CORREÇÃO** Arredondamento do subtotal dos itens na sincronização do pedido ( [#88](https://github.com/filipebezerra/LibertVendas/issues/88) )
* **CORREÇÃO** Sincronização de novos cadastros (Formas de pagamento, clientes e tabelas de preço) ( [#78](https://github.com/filipebezerra/LibertVendas/issues/78) )
* **CORREÇÃO** Campo tabela de preço padrão do cliente é ignorado quando possuir espaços em branco ( [#84](https://github.com/filipebezerra/LibertVendas/issues/84) )

Versão 1.0.3 (15/05/2017)
-----------------------
* **NOVO** Pedidos faturados podem ser visualizados no relatório e na listagem de pedidos
* **CORREÇÃO** Importação dos clientes sem vínculo com cidade/estado
* **MELHORIA** Código do produto e código de barras podem ser visualizados na listagem de itens do pedido
* **MELHORIA** Código do cliente pode ser visualizado na listagem de clientes
* **MELHORIA** As configurações alteradas pelo usuário são devidamente validadas

Versão 1.0.2 (06/05/2017)
-----------------------
* **CORREÇÃO** Pedidos de novos clientes não eram sincronizados manualmente
* **MELHORIA** Sincronização manual dos pedidos está mais rápida

Versão 1.0.1 (05/05/2017)
-----------------------
* **CORREÇÃO** Layout da tela Seus Pedidos em modo de seleção dos pedidos

Versão 1.0 (01/05/2017)
-----------------------
* Lançamento inicial do app android Libert Vendas

Beta 0.10.0 (03/02/2017)
-----------------------
* **NOVO** Gráficos dos pedidos por cliente

Beta 0.9.1 (21/01/2017)
-----------------------
* **CORREÇÃO** Teclado sempre aberto voltando do cadastro de pedido
* **CORREÇÃO** Recarregando os dados na troca de perfil
* **MELHORIA** Gesto swipe (PullToRefresh) para atualizar as listas

Beta 0.9.0 (15/01/2017)
-----------------------
* **NOVO** Troca de perfil do vendedor (empresas)
* **NOVO** Configuração do período da sincronização
* **NOVO** Integração com Crashlytics
* **MELHORIA** Comunicação com servidor, com 3 tentativas automáticas quando falha por timeout

Beta 0.8.3 (09/01/2017)
-----------------------
* **NOVO** Visualização do pedido enviado
* **CORREÇÃO** Visualização da aba Home sem conteúdo (temporário)
* **NOVO** Sincronização dos cadastros editados e novos de clientes e novos pedidos
* **NOVO** Menu lateral com perfis do vendedor
* **NOVO** Cadastros de clientes e dos pedidos podem ser editados
* **MELHORIA** Lista de clientes com CPF/CNPJ

Beta 0.5.1 (17/11/2016)
-----------------------
* **CORREÇÃO** Corrigido crash do app por falta de plugin da integração com Crashlytics

Beta 0.5.0 (17/11/2016)
-----------------------
* **MELHORIA** Configuração somente na primeira execução ( #18 )
* **MELHORIA** Login do vendedor somente na primeira execução ( #17 )
* **MELHORIA** Importação inicial somente na primeira execução ( #16 )
* **NOVO** Inclusão de pedidos sem sincronização com servidor ( #9 )

Beta 0.4.1 (07/11/2016)
-----------------------
* **CORREÇÃO** Corrigido cadastro de cliente com vínculo à estado/cidade
* **CORREÇÃO** Corrigido cadastro de cliente após salvar e retornar os dados salvos
* **CORREÇÃO** Corrigido importação incompleta dos dados obtidos do servidor

Beta 0.4.0 (07/11/2016)
-----------------------
* **MELHORIA** Tela de inclusão de cliente sem sincronização ( #8 )
* **NOVO** Tela de login do vendedor ( #15 )
* **NOVO** Tela de importação dos dados ( #14 )

Beta 0.3.1 (26/10/2016)
-----------------------
* **CORREÇÃO** Corrigido bug no RecyclerView
* **MELHORIA** Reduzido 1.5 Mb do apk
* **MELHORIA** Novo cliente cadastrado é inserido dinamicamente na tela da lista de clientes

Beta 0.3.0 (25/10/2016)
-----------------------
* **MELHORIA** Design aprimorado
* **NOVO** Inclusão de clientes sem sincronia com servidor

Beta 0.2.0 (18/10/2016)
-----------------------
* **MELHORIA** Suporte ao Android Jelly Bean (4.1.x)
* **NOVO** Tela da lista de pedidos com abas
* **NOVO** Tela de inclusão de pedido
* **NOVO** Tela de inclusão de cliente

Beta 0.1.0 (06/10/2016)
-----------------------
* **NOVO** Tela de lançamento personalizada (Splashscreen)
* **NOVO** Tela das configurações do app
* **NOVO** Menu lateral
* **NOVO** Tela da lista de produtos
* **NOVO** Tela da lista de clientes
* **NOVO** Tela da lista de produtos
