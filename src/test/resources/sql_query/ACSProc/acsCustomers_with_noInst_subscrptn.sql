--non-institution records
select s.ctm_nbr, a.atn_1st, a.atn_end, e.adr_emal
from cirsub_m s
inner join cdsadr_m a on s.ctm_nbr = a.ctm_nbr and s.adr_cde = a.adr_cde and a.adr_flg = '0'
inner join cdseml_m e on a.emal_id = e.emal_id
where
s.pub_cde = 'nej' and s.sub_typ = 'phy' and s.crc_sts = 'r' and s.key_cde <> 'agency'