create table public.articles
(
    id                serial
        constraint articles_pk
            primary key,
    origin_url        text,
    author_full_name  text,
    categories        text[],
    source_publisher  text,
    publish_date      date,
    scanned_timestamp timestamp,
    html_content      varchar not null
);

alter table public.articles
    owner to tbnvxiimeykkzr;

create index articles_id_index
    on public.articles (id);

