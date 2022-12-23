create table if not exists public.rss_xml_storage
(
    id            serial
        constraint rss_xml_storage_pk
            primary key,
    feed_document xml,
    feed_src_url  varchar not null
);

alter table public.rss_xml_storage
    owner to tbnvxiimeykkzr;

create unique index if not exists rss_xml_storage_feed_src_url_uindex
    on public.rss_xml_storage (feed_src_url);

create unique index if not exists rss_xml_storage_id_uindex
    on public.rss_xml_storage (id);

