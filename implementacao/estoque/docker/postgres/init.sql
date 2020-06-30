--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id_cliente bigint NOT NULL,
    nome character varying(255) NOT NULL,
    cpf character varying(11) NOT NULL,
    telefone character varying(11) NOT NULL,
    endereco character varying(255) NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: cliente_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_cliente_seq OWNER TO postgres;

--
-- Name: cliente_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_cliente_seq OWNED BY public.cliente.id_cliente;


--
-- Name: estoque; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estoque (
    id_estoque bigint NOT NULL,
    fk_id_filial bigint NOT NULL,
    fk_id_produto bigint NOT NULL,
    quantidade integer NOT NULL
);


ALTER TABLE public.estoque OWNER TO postgres;

--
-- Name: estoque_id_estoque_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.estoque_id_estoque_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.estoque_id_estoque_seq OWNER TO postgres;

--
-- Name: estoque_id_estoque_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.estoque_id_estoque_seq OWNED BY public.estoque.id_estoque;


--
-- Name: filial; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.filial (
    id_filial bigint NOT NULL,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.filial OWNER TO postgres;

--
-- Name: filial_id_filial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.filial_id_filial_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.filial_id_filial_seq OWNER TO postgres;

--
-- Name: filial_id_filial_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.filial_id_filial_seq OWNED BY public.filial.id_filial;


--
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forma_pagamento (
    id_forma_pagamento bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.forma_pagamento OWNER TO postgres;

--
-- Name: forma_pagamento_id_forma_pagamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.forma_pagamento_id_forma_pagamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forma_pagamento_id_forma_pagamento_seq OWNER TO postgres;

--
-- Name: forma_pagamento_id_forma_pagamento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forma_pagamento_id_forma_pagamento_seq OWNED BY public.forma_pagamento.id_forma_pagamento;


--
-- Name: item_pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item_pedido (
    id_item_pedido bigint NOT NULL,
    fk_id_pedido_estoque bigint NOT NULL,
    fk_id_produto bigint NOT NULL,
    fk_id_status_item_pedido bigint NOT NULL,
    quantidade integer NOT NULL,
    valor_unitario numeric
);


ALTER TABLE public.item_pedido OWNER TO postgres;

--
-- Name: item_pedido_id_item_pedido_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.item_pedido_id_item_pedido_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.item_pedido_id_item_pedido_seq OWNER TO postgres;

--
-- Name: item_pedido_id_item_pedido_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.item_pedido_id_item_pedido_seq OWNED BY public.item_pedido.id_item_pedido;


--
-- Name: pedido_estoque; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedido_estoque (
    id_pedido_estoque bigint NOT NULL,
    fk_id_tipo_pedido_estoque bigint NOT NULL,
    fk_id_filial bigint NOT NULL,
    fk_id_usuario bigint NOT NULL,
    fk_id_cliente bigint,
    fk_id_forma_pagamento bigint,
    observacao text
);


ALTER TABLE public.pedido_estoque OWNER TO postgres;

--
-- Name: pedido_estoque_id_pedido_estoque_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedido_estoque_id_pedido_estoque_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pedido_estoque_id_pedido_estoque_seq OWNER TO postgres;

--
-- Name: pedido_estoque_id_pedido_estoque_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedido_estoque_id_pedido_estoque_seq OWNED BY public.pedido_estoque.id_pedido_estoque;


--
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    id_produto bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    codigo_barras character varying(13) NOT NULL,
    valor_unitario numeric NOT NULL
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- Name: produto_id_produto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_id_produto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_id_produto_seq OWNER TO postgres;

--
-- Name: produto_id_produto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_id_produto_seq OWNED BY public.produto.id_produto;


--
-- Name: status_item_pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.status_item_pedido (
    id_status_item_pedido bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.status_item_pedido OWNER TO postgres;

--
-- Name: status_item_pedido_id_status_item_pedido_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.status_item_pedido_id_status_item_pedido_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.status_item_pedido_id_status_item_pedido_seq OWNER TO postgres;

--
-- Name: status_item_pedido_id_status_item_pedido_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.status_item_pedido_id_status_item_pedido_seq OWNED BY public.status_item_pedido.id_status_item_pedido;


--
-- Name: tipo_pedido_estoque; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_pedido_estoque (
    id_tipo_pedido_estoque bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.tipo_pedido_estoque OWNER TO postgres;

--
-- Name: tipo_pedido_estoque_id_tipo_pedido_estoque_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_pedido_estoque_id_tipo_pedido_estoque_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_pedido_estoque_id_tipo_pedido_estoque_seq OWNER TO postgres;

--
-- Name: tipo_pedido_estoque_id_tipo_pedido_estoque_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_pedido_estoque_id_tipo_pedido_estoque_seq OWNED BY public.tipo_pedido_estoque.id_tipo_pedido_estoque;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id_usuario bigint NOT NULL,
    nome character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    senha character varying(255) NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_usuario_seq OWNER TO postgres;

--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;


--
-- Name: cliente id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id_cliente SET DEFAULT nextval('public.cliente_id_cliente_seq'::regclass);


--
-- Name: estoque id_estoque; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque ALTER COLUMN id_estoque SET DEFAULT nextval('public.estoque_id_estoque_seq'::regclass);


--
-- Name: filial id_filial; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filial ALTER COLUMN id_filial SET DEFAULT nextval('public.filial_id_filial_seq'::regclass);


--
-- Name: forma_pagamento id_forma_pagamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento ALTER COLUMN id_forma_pagamento SET DEFAULT nextval('public.forma_pagamento_id_forma_pagamento_seq'::regclass);


--
-- Name: item_pedido id_item_pedido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_pedido ALTER COLUMN id_item_pedido SET DEFAULT nextval('public.item_pedido_id_item_pedido_seq'::regclass);


--
-- Name: pedido_estoque id_pedido_estoque; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_estoque ALTER COLUMN id_pedido_estoque SET DEFAULT nextval('public.pedido_estoque_id_pedido_estoque_seq'::regclass);


--
-- Name: produto id_produto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN id_produto SET DEFAULT nextval('public.produto_id_produto_seq'::regclass);


--
-- Name: status_item_pedido id_status_item_pedido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_item_pedido ALTER COLUMN id_status_item_pedido SET DEFAULT nextval('public.status_item_pedido_id_status_item_pedido_seq'::regclass);


--
-- Name: tipo_pedido_estoque id_tipo_pedido_estoque; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pedido_estoque ALTER COLUMN id_tipo_pedido_estoque SET DEFAULT nextval('public.tipo_pedido_estoque_id_tipo_pedido_estoque_seq'::regclass);


--
-- Name: usuario id_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (id_cliente, nome, cpf, telefone, endereco) FROM stdin;
4	Marlene	17752812056	98999998888	Av. 05, 19, Jardim América
5	Hildebrando	80025180045	98988889999	Rua 36, 1, Jardim América
\.


--
-- Data for Name: estoque; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estoque (id_estoque, fk_id_filial, fk_id_produto, quantidade) FROM stdin;
1	1	1	30
2	1	2	40
3	1	3	50
4	1	4	100
5	1	5	60
6	1	6	75
7	1	7	60
8	1	8	80
9	1	9	25
10	1	10	70
11	2	1	50
12	2	2	25
13	2	3	100
14	2	4	40
15	2	5	30
16	2	6	60
17	2	7	75
18	2	8	65
19	2	9	80
20	2	10	50
\.


--
-- Data for Name: filial; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.filial (id_filial, nome) FROM stdin;
2	Supermercado Mateus Tropical
1	Mix Mateus João Paulo
\.


--
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.forma_pagamento (id_forma_pagamento, descricao) FROM stdin;
1	À VISTA
2	BOLETO
3	CARTÃO
\.


--
-- Data for Name: item_pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.item_pedido (id_item_pedido, fk_id_pedido_estoque, fk_id_produto, fk_id_status_item_pedido, quantidade, valor_unitario) FROM stdin;
2	1	8	1	5	8.25
1	1	1	1	3	4.5
3	2	3	1	4	3.5
4	2	5	1	5	4.5
5	2	7	2	3	6.5
\.


--
-- Data for Name: pedido_estoque; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pedido_estoque (id_pedido_estoque, fk_id_tipo_pedido_estoque, fk_id_filial, fk_id_usuario, fk_id_cliente, fk_id_forma_pagamento, observacao) FROM stdin;
1	1	2	1	\N	\N	\N
2	2	2	1	4	1	obs
\.


--
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produto (id_produto, descricao, codigo_barras, valor_unitario) FROM stdin;
3	Biscoito Racheiro	5687544567456	3.5
6	Amaciante Downy	8207544567453	6.23
1	Leite Líquido Betania	1234567890128	4.5
8	Manteiga Natural da Vaca	3177773322213	8.25
4	Achocolatado Tres Coracoes	2104567890123	5.5
10	Condicionador Palmolive	4581112224516	7.80
2	Barra de Chocolate Talento	5554443322212	5.35
7	Batata Palha Yoki	5563287890122	6.50
9	Shampoo Clear Anticaspa	7964694567450	13.50
5	Fécula Amafil	4154443322215	4.5
\.


--
-- Data for Name: status_item_pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.status_item_pedido (id_status_item_pedido, descricao) FROM stdin;
1	ATIVO
2	CANCELADO
3	PROCESSADO
\.


--
-- Data for Name: tipo_pedido_estoque; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipo_pedido_estoque (id_tipo_pedido_estoque, descricao) FROM stdin;
1	ENTRADA
2	SAÍDA
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id_usuario, nome, username, senha) FROM stdin;
1	Paulo Gustavo	paulogustavo	paulo@ithappens
\.


--
-- Name: cliente_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_cliente_seq', 5, true);


--
-- Name: estoque_id_estoque_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.estoque_id_estoque_seq', 20, true);


--
-- Name: filial_id_filial_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.filial_id_filial_seq', 2, true);


--
-- Name: forma_pagamento_id_forma_pagamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forma_pagamento_id_forma_pagamento_seq', 3, true);


--
-- Name: item_pedido_id_item_pedido_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.item_pedido_id_item_pedido_seq', 5, true);


--
-- Name: pedido_estoque_id_pedido_estoque_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedido_estoque_id_pedido_estoque_seq', 2, true);


--
-- Name: produto_id_produto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_id_produto_seq', 10, true);


--
-- Name: status_item_pedido_id_status_item_pedido_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.status_item_pedido_id_status_item_pedido_seq', 3, true);


--
-- Name: tipo_pedido_estoque_id_tipo_pedido_estoque_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_pedido_estoque_id_tipo_pedido_estoque_seq', 2, true);


--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 1, true);


--
-- Name: cliente cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (id_cliente);


--
-- Name: estoque estoque_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_pk PRIMARY KEY (id_estoque);


--
-- Name: filial filial_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.filial
    ADD CONSTRAINT filial_pk PRIMARY KEY (id_filial);


--
-- Name: forma_pagamento forma_pagamento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pk PRIMARY KEY (id_forma_pagamento);


--
-- Name: item_pedido item_pedido_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_pk PRIMARY KEY (id_item_pedido);


--
-- Name: pedido_estoque pedido_estoque_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_estoque
    ADD CONSTRAINT pedido_estoque_pk PRIMARY KEY (id_pedido_estoque);


--
-- Name: produto produto_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pk PRIMARY KEY (id_produto);


--
-- Name: status_item_pedido status_item_pedido_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.status_item_pedido
    ADD CONSTRAINT status_item_pedido_pk PRIMARY KEY (id_status_item_pedido);


--
-- Name: tipo_pedido_estoque tipo_pedido_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pedido_estoque
    ADD CONSTRAINT tipo_pedido_pk PRIMARY KEY (id_tipo_pedido_estoque);


--
-- Name: usuario usuario_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pk PRIMARY KEY (id_usuario);


--
-- Name: cliente_cpf_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX cliente_cpf_uindex ON public.cliente USING btree (cpf);


--
-- Name: produto_codigo_barras_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX produto_codigo_barras_uindex ON public.produto USING btree (codigo_barras);


--
-- Name: usuario_username_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX usuario_username_uindex ON public.usuario USING btree (username);


--
-- Name: estoque estoque_filial_id_filial_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_filial_id_filial_fk FOREIGN KEY (fk_id_filial) REFERENCES public.filial(id_filial);


--
-- Name: estoque estoque_produto_id_produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estoque
    ADD CONSTRAINT estoque_produto_id_produto_fk FOREIGN KEY (fk_id_produto) REFERENCES public.produto(id_produto);


--
-- Name: item_pedido item_pedido_pedido_estoque_id_pedido_estoque_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_pedido_estoque_id_pedido_estoque_fk FOREIGN KEY (fk_id_pedido_estoque) REFERENCES public.pedido_estoque(id_pedido_estoque);


--
-- Name: item_pedido item_pedido_produto_id_produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_produto_id_produto_fk FOREIGN KEY (fk_id_produto) REFERENCES public.produto(id_produto);


--
-- Name: item_pedido item_pedido_status_item_pedido_id_status_item_pedido_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_status_item_pedido_id_status_item_pedido_fk FOREIGN KEY (fk_id_status_item_pedido) REFERENCES public.status_item_pedido(id_status_item_pedido);


--
-- Name: pedido_estoque pedido_estoque_cliente_id_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_estoque
    ADD CONSTRAINT pedido_estoque_cliente_id_cliente_fk FOREIGN KEY (fk_id_cliente) REFERENCES public.cliente(id_cliente);


--
-- Name: pedido_estoque pedido_estoque_filial_id_filial_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_estoque
    ADD CONSTRAINT pedido_estoque_filial_id_filial_fk FOREIGN KEY (fk_id_filial) REFERENCES public.filial(id_filial);


--
-- Name: pedido_estoque pedido_estoque_forma_pagamento_id_forma_pagamento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_estoque
    ADD CONSTRAINT pedido_estoque_forma_pagamento_id_forma_pagamento_fk FOREIGN KEY (fk_id_forma_pagamento) REFERENCES public.forma_pagamento(id_forma_pagamento);


--
-- Name: pedido_estoque pedido_estoque_tipo_pedido_estoque_id_tipo_pedido_estoque_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_estoque
    ADD CONSTRAINT pedido_estoque_tipo_pedido_estoque_id_tipo_pedido_estoque_fk FOREIGN KEY (fk_id_tipo_pedido_estoque) REFERENCES public.tipo_pedido_estoque(id_tipo_pedido_estoque);


--
-- Name: pedido_estoque pedido_estoque_usuario_id_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_estoque
    ADD CONSTRAINT pedido_estoque_usuario_id_usuario_fk FOREIGN KEY (fk_id_usuario) REFERENCES public.usuario(id_usuario);


--
-- PostgreSQL database dump complete
--

