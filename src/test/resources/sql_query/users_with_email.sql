Select TOP 20 ve.BIL_CTM, ve.ORD_NBR, ve.PO_NUM, ap.SRV_TYP, st.ABIL_STS, st.AGR_STS,
       e.ADR_EMAL, ve.VER_STRT,ATN_1ST, ATN_END, (TAX_AMT + ITM_NET) as AMT_DUE
from AMBAPT_M ap inner join AMBSTS_N st on ap.AGR_ID = st.AGR_ID and ap.AGR_VER = st.AGR_VER and ap.AGR_TRM = st.AGR_TRM
                 inner join AMBVER_M ve on ap.AGR_ID = ve.AGR_ID and ap.AGR_VER = ve.AGR_VER and ap.AGR_TRM = ve.AGR_TRM
                 inner join CDSADR_M a on ve.BIL_CTM = a.CTM_NBR and a.ADR_CDE = ve.BIL_ADR and a.ADR_FLG = '0'
                 inner join CDSEML_M e on a.EMAL_ID = e.EMAL_ID where ap.SRV_TYP like 'CATJ%'
                                                                  and st.ABIL_STS in ('B') and st.AGR_STS in ('A', 'W', 'B')
                                                                  and VER_STRT <= ?
                                                                  and PO_NUM <> '' order by VER_STRT desc