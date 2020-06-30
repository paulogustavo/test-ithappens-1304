-- Consulta que traz todos os produtos com quantidade maior ou igual a 100
select p.id_produto, p.descricao, sum(e.quantidade)
from produto p
inner join estoque e on p.id_produto = e.fk_id_produto
group by p.id_produto, p.descricao
having sum(e.quantidade) >= 100;

-- Consulta que traz todos os produtos que tëm estoque para a filial de código 60
select p.id_produto, p.descricao, sum(e.quantidade)
from estoque e
inner join produto p on e.fk_id_produto = p.id_produto
where e.fk_id_filial = 60
group by p.id_produto, p.descricao
having sum(e.quantidade) > 0;

-- Consulta que lista todos os campos para o domínio PedidoEstoque e ItemPedido
-- filtrando apenas o produto de código 7993
select *
from pedido_estoque pe
inner join item_pedido ip on pe.id_pedido_estoque = ip.fk_id_pedido_estoque
where ip.fk_id_pedido_estoque = 7993;

-- Consulta que lista os pedidos com suas respectivas formas de pagamento
select pe.*, fp.descricao
from pedido_estoque pe
inner join forma_pagamento fp on pe.fk_id_forma_pagamento = fp.id_forma_pagamento
where pe.fk_id_forma_pagamento is not null;
;

-- Consulta que sumariza e bate os valores da capa do pedido com os valores dos itens de pedido
select pe.id_pedido_estoque,
       p.descricao as descricao_produto,
       sum(ip.quantidade) as quantidade,
       ip.valor_unitario,
       sum(ip.valor_unitario * ip.quantidade) as valor_total
from pedido_estoque pe
         inner join item_pedido ip on pe.id_pedido_estoque = ip.fk_id_pedido_estoque
         inner join produto p on ip.fk_id_produto = p.id_produto
where ip.fk_id_status_item_pedido <> 2 --retirando da consulta os itens cancelados
group by pe.id_pedido_estoque, p.descricao, ip.valor_unitario;

-- Consulta para sumarizar o total dos itens por pedido e que filtre apenas
-- os pedidos nos quais a soma total da quantidade de itens de pedido seja maior que 10
select pe.id_pedido_estoque, sum(ip.quantidade)
from pedido_estoque pe
         inner join item_pedido ip on pe.id_pedido_estoque = ip.fk_id_pedido_estoque
where ip.fk_id_status_item_pedido <> 2 -- Não considerando os itens cancelados
group by pe.id_pedido_estoque
having sum(ip.quantidade) > 10;
