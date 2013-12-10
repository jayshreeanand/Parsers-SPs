GO
/****** Object:  StoredProcedure [dbo].[ecc_Customized_reports]    ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


--ALTER PROCEDURE [dbo].[ecc_Customized_reports]
--	@sTime datetime,
--	@eTime datetime,
--	@base varchar(10),
--    @level varchar(20),
--    @KPI varchar(20),
--    @Criteria varchar(20)=NULL

	
--AS
DECLARE @sTime datetime,
	@eTime datetime,
	@base varchar(10),
	@level varchar(10),
	@KPI varchar(20)


Set @stime='2012-07-10 '
Set @etime='2012-07-11'
Set @base='day'
set @level ='item'
Set @KPI ='PSR'




Declare  @NODE varchar(16),  @vqry varchar(max)
Declare @iNODE int,@nNODEs int,@NDAYS INT,@IDAYS INT,@vNODE varchar(32), @KPIVAL varchar(32), @tempTime datetime, @temptimechar varchar(32)
Declare @ATT varchar(32),@SEL VARCHAR(32), @SUCC varchar(32),@db varchar(max), @TOTATT real,@VITEM varchar(32),@EXTRA VARCHAR(MAX) ,@TOTSUCC real,@selquery varchar(max)

-- FOR EOS WISE TREND --> SET @LEVEL='ITEM' , SET @CRITERIA ='3745'
-- FOR NODE WISE EOS TREND -->SET @LEVEL ='NODE', SET @CRITERIA ='KK-KKSHU01'
-- CRITERIA APPLICABLE ONLY FOR EOS

Set @criteria = '100'
--- Setting the right Date Time.
IF @KPI ='EOS' 
begin
set @level ='ITEM'
end
IF @stime = ''
	BEGIN
		set @stime = convert(varchar(25),getdate()-1,23)+' 00:00:00'
	END
	IF @stime = ''
	BEGIN
		set @etime = convert(varchar(25),getdate()-1,23)+' 00:00:00'
	END
Set @STIME=convert(varchar(25),@stime,23)+' 00:00:00' 	
Set @ETIME=convert(varchar(25),@etime,23)+' 23:00:00'
Set @EXTRA =''
SET @sel ='ITEM=1,'
IF @level ='ITEM'
BEGIN
SET @SEL='ITEM,'
END
IF @KPI ='OGHOSR' 
begin
Set @selquery = 'STR(CASE WHEN (NNBRHBANTOT+NNBRHSNATOT) =0 THEN NULL ELSE 100*((NNBRHBANSUCC+NNBRHSNASUCC)/(NNBRHBANTOT+NNBRHSNATOT)) END,8,2) as KPIVAL, (NNBRHBANTOT+NNBRHSNATOT) AS ATT, (NNBRHBANSUCC+NNBRHSNASUCC) AS SUCC'
Set @db ='dbo.ecc_sts_NBRMSCLST'
end
IF @KPI ='AUTH'
begin
set @selquery ='str(case when (NAUTREQTOT)=0 then null else (NAUTREQSUCC)/(NAUTREQTOT)*100 end,8,2) as KPIVAL, NAUTREQTOT AS ATT, NAUTREQSUCC AS SUCC'
Set @db ='dbo.ecc_sts_sechand'
end
IF @KPI ='CHANNELALLOC'
begin
set @selquery ='str(case when (NCHAFRMTOT+NCHATOMTOT)=0 then null else 100*(NCHAFRMSUCC+NCHATOMSUCC)/(NCHAFRMTOT+NCHATOMTOT) end,8,2),(NCHAFRMTOT+NCHATOMTOT)AS TOT, (NCHAFRMSUCC+NCHATOMSUCC) AS SUCC'
set @db ='dbo.ecc_sts_chassignt'
end
IF @KPI ='PROCLOAD'
begin
set @level ='node'
set @selquery ='str(case when (NSCAN)=0 then null else (ACCLOAD)/(NSCAN) end,8,2) as KPIVAL, ACCLOAD AS ATT, NSCAN AS SUCC'
Set @db ='dbo.ecc_sts_loas'
end
IF @KPI ='CIPH'
begin
set @selquery ='str(case when (NCIPATTTOT)=0 then null else (NCIPSETSUCC)/(NCIPATTTOT)*100 end,8,2) as KPIVAL, NCIPATTTOT AS ATT, NCIPSETSUCC AS SUCC'
Set @db ='.dbo.ecc_sts_sechand'
end
IF @KPI ='ICHOSR' 
begin
Set @selquery = 'STR(CASE WHEN (NNBRHINATOT+NNBRHSANTOT) =0 THEN NULL ELSE 100*((NNBRHINASUCC+NNBRHSANSUCC)/(NNBRHINATOT+NNBRHSANTOT)) END,8,2) as KPIVAL, (NNBRHINATOT+NNBRHSANTOT) AS ATT, (NNBRHINASUCC+NNBRHSANSUCC) AS SUCC'
Set @db ='dbo.ecc_sts_NBRMSCLST'
end
IF @KPI ='PSR' AND @LEVEL ='ITEM'
begin
Set @selquery = 'STR(CASE WHEN NLAPAG1LOTOT =0 THEN NULL ELSE 100*((NLAPAG1RESUCC+NLAPAG2RESUCC)/NLAPAG1LOTOT) END,8,2) as KPIVAL, NLAPAG1LOTOT AS ATT, (NLAPAG1RESUCC+NLAPAG2RESUCC) AS SUCC'
Set @db ='dbo.ecc_sts_LOCAREAST'
end
IF @KPI ='LUSR' AND @LEVEL ='NODE'
begin
Set @selquery = 'STR(CASE WHEN (NLOCOLDTOT)=0 then null else (NLOCOLDSUCC)/(NLOCOLDTOT)*100  END,8,2) as KPIVAL, NLOCOLDTOT AS ATT, NLOCOLDSUCC AS SUCC'
Set @db ='dbo.ecc_sts_updlocat'
end
IF @KPI ='LUSR' AND @LEVEL ='ITEM'
begin
Set @selquery = 'STR(CASE WHEN (NLALIOTOT+NLALNOTOT+NLALPETOT)=0 then null else ((NLALIOSUCC+NLALNOSUCC+NLALPESUCC)/(NLALIOTOT+NLALNOTOT+NLALPETOT))*100  END,8,2) as KPIVAL, (NLALIOTOT+NLALNOTOT+NLALPETOT) AS ATT, (NLALIOSUCC+NLALNOSUCC+NLALPESUCC) AS SUCC'
Set @db ='dbo.ecc_sts_LOCAREAST'
end
IF @KPI ='PSR' AND @LEVEL ='NODE'
begin
Set @selquery = 'STR(CASE WHEN NPAG1LOTOT =0 THEN NULL ELSE 100*((NPAG1RESUCC+NPAG2RESUCC)/NPAG1LOTOT) END,8,2) as KPIVAL, NPAG1LOTOT AS ATT, (NPAG1RESUCC+NPAG2RESUCC) AS SUCC'
Set @db ='dbo.ecc_sts_PAGING'
end
IF @KPI ='EOS' 
begin
SET @SEL='ITEM,'
Set @selquery = 'NEVERY as KPIVAL, NEVERY AS ATT, 1 AS SUCC'
Set @db ='dbo.ecc_sts_eos'
Set @EXTRA ='AND '+@level+' ='''+@criteria+''''
set @level ='ITEM'
end
---3G KPI
IF @KPI ='3GPSR' AND @LEVEL ='NODE'
begin
Set @selquery = 'STR(case when (NPAG1LOuTOT)=0 then null else 100*(((NPAG1REUSUCC)+(NPAG2REUSUCC))/(NPAG1LOuTOT))end ,8,2)   AS KPIVAL,    NPAG1LOuTOT AS TOT, (NPAG1REUSUCC)+(NPAG2REUSUCC) AS SUCC'
Set @db ='ecc_sts_paging'
end
IF @KPI ='3GPSR' AND @LEVEL ='ITEM'
begin
Set @selquery = 'STR(CASE WHEN NLAPAG1LOTOT =0 THEN NULL ELSE 100*((NLAPAG1RESUCC+NLAPAG2RESUCC)/NLAPAG1LOTOT) END,8,2) as KPIVAL, NLAPAG1LOTOT AS ATT, (NLAPAG1RESUCC+NLAPAG2RESUCC) AS SUCC'
Set @db ='ecc_sts_locareast'
Set @EXTRA ='AND ITEM IN (select ITEM from dbo.ecc_sts_3GLAC)'
end
IF @KPI ='3GLUSR' AND @LEVEL ='ITEM'
begin

Set @selquery = 'STR(CASE WHEN (NLALIOTOT+NLALNOTOT+NLALPETOT)=0 then null else ((NLALIOSUCC+NLALNOSUCC+NLALPESUCC)/(NLALIOTOT+NLALNOTOT+NLALPETOT))*100  END,8,2) as KPIVAL, (NLALIOTOT+NLALNOTOT+NLALPETOT) AS ATT, (NLALIOSUCC+NLALNOSUCC+NLALPESUCC) AS SUCC'
Set @db ='dbo.ecc_sts_LOCAREAST'
Set @EXTRA ='AND ITEM IN (select ITEM from dbo.ecc_sts_3GLAC)'
end

IF @KPI ='3GCIPH' 
begin
Set @selquery = 'STR(case when (NSECTORNTOT)=0 then null else 100*((NSECFRRNSCC)/(NSECTORNTOT))end ,8,2)AS KPIVAL, NSECTORNTOT AS ATT, NSECFRRNSCC AS SUCC'
                                     
Set @db ='ecc_sts_UMTSSEC'
end
IF @KPI ='VLRA' 
begin
Set @selquery = 'STR(case when (NHLRWREGAST)=0 then null else NHLRWREGAST end ,8,0)AS KPIVAL, NHLRWREGAST AS ATT, 1 AS SUCC'
                                     
Set @db ='ecc_sts_hlrstat'
end
IF @KPI ='VLRR' 
begin
Set @selquery = 'STR(case when (NHLRWMSST)=0 then null else NHLRWMSST end ,8,0)AS KPIVAL, NHLRWMSST AS ATT, 1 AS SUCC'
                                     
Set @db ='ecc_sts_hlrstat'
end
--inter msc 3g to 2g og hosr
IF @KPI ='3GOGHOSR' 
begin
Set @selquery = 'STR(case when ((NNBRBUGATOT))=0 then null else 100*((NNBRBUGASUCC)/(NNBRBUGATOT))end ,8,2) as KPIVAL, NNBRBUGATOT AS ATT, (NNBRBUGASUCC) AS SUCC'
Set @db ='ecc_sts_NBRMSCUGHO'
end
--inter msc 3g to 2g ic hosr
IF @KPI ='3GICHOSR' 
begin
Set @selquery = 'STR(case when ((NNBRIHOATCHTOT))=0 then null else 100*((NNBRSIHOATCHSUCC)/(NNBRIHOATCHTOT))end ,8,2) as KPIVAL, NNBRIHOATCHTOT AS ATT, (NNBRSIHOATCHSUCC) AS SUCC'
Set @db ='ecc_sts_NBRMSCUGHO'
end

--intra msc 3g to 2g hosr
IF @KPI ='3GINTRAHOSR' 
begin
Set @selquery = 'STR(case when ((NUGHRNCBSCTOT))=0 then null else 100*((NUGHRNCBSCSUCC)/(NUGHRNCBSCTOT))end ,8,2) as KPIVAL, NUGHRNCBSCTOT AS ATT, (NUGHRNCBSCSUCC) AS SUCC'
Set @db ='ecc_sts_UGHNDOVER'
end

IF @KPI ='3GRAB' 
begin
Set @selquery = 'STR(case when ((NRNFRMTOTI+NRNTOMTOTO))=0 then null else 100*((NRNFRMSCCI+NRNTOMSCCO)/(NRNFRMTOTI+NRNTOMTOTO))end ,8,2)as KPIVAL, (NRNFRMTOTI+NRNTOMTOTO) AS ATT, (NRNFRMSCCI+NRNTOMSCCO) AS SUCC'
Set @db ='ecc_sts_RNCSTAT'
end
--PENDING
IF @KPI ='3GLUSR' AND @LEVEL ='NODE'
begin
Set @selquery = 'STR(CASE WHEN sum(NLALIOTOT+NLALNOTOT+NLALPETOT)=0 then null else (sum(NLALIOSUCC+NLALNOSUCC+NLALPESUCC)/sum(NLALIOTOT+NLALNOTOT+NLALPETOT))*100  END,8,2) as KPIVAL, sum(NLALIOTOT+NLALNOTOT+NLALPETOT) AS ATT, sum(NLALIOSUCC+NLALNOSUCC+NLALPESUCC) AS SUCC'
Set @db ='dbo.ecc_sts_LOCAREAST'
Set @EXTRA ='AND ITEM IN (select ITEM from dbo.ecc_sts_3GLAC)'
end
       
if object_id('tempdb..#temp12') is not null
begin
drop table #temp12
end
if object_id('tempdb..#temp13') is not null
begin
drop table #temp13
end

if object_id('tempdb..#temp14') is not null
begin
drop table #temp14
end
if object_id('tempdb..#FINALOUTPUT') is not null
begin
drop table #FINALOUTPUT
end
if object_id('tempdb..#tempNODE') is not null
begin
  drop table #tempNODE
end
		if object_id('tempdb..#tempNODE1') is not null
begin
  drop table #tempNODE1
end
	if object_id('tempdb..#tempNODE2') is not null
begin
  drop table #tempnode2
end
		if object_id('tempdb..#tempDATE') is not null
begin
  drop table #tempDATE
end
BEGIN
create table #finalOutput(NODE varchar(30), ITEM VARCHAR(32))
insert into #finalOutput (NODE,ITEM) values ('OVERALL','OVERALL')
create table #temp12([STIME] DATETIME,[NODE] varchar(32) , [ITEM]varchar(32), [KPIVAL] varchar(32),[ATT] VARCHAR(32), [SUCC] varchar(32))
Set @vqry ='insert into #temp12 select STIME, NODE,'+@SEL+@selquery + '  from '+@db+'_'+@level+'_'+@base+ ' WHERE (stime BETWEEN  '''+rtrim(CONVERT(DATETIME,@STIME,120))+'''AND '''+rtrim(CONVERT(DATETIME,@ETIME,120))+''') '+@EXTRA+''  

if @KPI ='3GLUSR' AND @level ='NODE'
BEGIN
create table #temp13([STIME] DATETIME,[NODE] varchar(32) , [KPIVAL] varchar(32),[ATT] VARCHAR(32), [SUCC] varchar(32))
Set @vqry ='insert into #temp13 select STIME, NODE,'+@selquery + '  from '+@db+'_item_'+@base+ ' WHERE (stime BETWEEN  '''+rtrim(CONVERT(DATETIME,@STIME,120))+'''AND '''+rtrim(CONVERT(DATETIME,@ETIME,120))+''') '+@EXTRA+'GROUP BY NODE,stime order by stime, node asc'  

exec (@vqry)
Set @vqry ='insert into #temp12 select STIME, NODE,ITEM=1,KPIVAL, ATT , SUCC  FROM #TEMP13'

END

exec(@vqry)

SELECT DISTINCT  STIME ,IDDATE =IDENTITY(INT,1,1)INTO #TEMPDATE FROM #TEMP12 order by STIME asc

SELECT @nDAYS = MAX(idDATE) from #TEMPDATE
 SELECT  NODE,ITEM,MIN(ATT) AS MINATT   INTO #TEMPNODE1 FROM #TEMP12 GROUP BY ITEM,NODE order by NODE asc

 --NOT APPLICABLE FOR EOS AS EOS CAN BE '0' IN MANY CASES
IF(NOT (@KPI ='EOS' AND @level ='ITEM')) 

BEGIN

DELETE FROM #TEMPNODE1 where (MINATT ='0' OR MINATT =NULL)
END

SELECT DISTINCT (NODE+ITEM)AS NODEITEM, NODE, ITEM  INTO #TEMPNODE2 FROM #TEMPNODE1 order by NODE asc
SELECT NODE, ITEM, IDNODE = IDENTITY(INT,1,1) INTO #TEMPNODE FROM #TEMPNODE2 ORDER BY NODE ASC, ITEM ASC
DROP TABLE #TEMPNODE1
DROP TABLE #TEMPNODE2
SELECT @nNODEs = MAX(idNODE) from #TEMPNODE
Set @iNODE = 0
WHILE @iNODE <@nNODEs 
BEGIN
Set @iNODE += 1
	select @vNODE = NODE ,@vitem =ITEM from #TEMPNODE where idNODE = @iNODE
	insert into #finalOutput (NODE,ITEM) values (@vNODE,@vitem)
end
Set @totATT =0
Set @ATT =0
Set @totSUCC =0
Set @SUCC =0
Set @KPIVAL =0
Set @iNODE = 0
Set @iDAYS = 0
WHILE @iDAYS <@nDAYS 
begin
Set @IDAYS +=1
SELECT @TEMPTIME =STIME FROM #TEMPDATE where IDDATE =@idays
Set @tempTimechar=CAST (convert (varchar,(@tempTime),120) as varchar(32))
if @inode =0 
begin
Set @vqry='alter table #finalOutput add [' + @temptimechar + '] varchar(32)'
exec(@vqry)
end
WHILE @iNODE <@nNODEs
begin
Set @iNODE += 1
select @vNODE = NODE, @VITEM =ITEM from #TEMPNODE where idNODE = @iNODE
SELECT @KPIVAL =KPIVAL, @ATT =ATT, @SUCC =SUCC  From #TEMP12 where  NODE= @vnode AND ITEM =@VITEM and convert(varchar,STIME,120) = convert(varchar,@temptime,120)

Set @TOTATT = @TOTATT +@ATT
Set @TOTSUCC =@TOTSUCC+@SUCC
Set @vqry='update #finalOutput Set [' + @temptimechar + ']='''+ @KPIVAL+ ''' where NODE =''' + @vNODE + '''and item ='''+@vitem+''''
exec (@vqry)
Set @kpival =0
Set @ATT =0
Set @SUCC =0
end
IF @KPI = 'EOS' OR  @KPI ='VLRA' OR @KPI ='VLRR' 
BEGIN
Set @KPIVAL =@TOTATT
END 
ELSE
BEGIN
Set @KPIVAL =STR(CASE WHEN @TOTATT= 0 THEN NULL ELSE 100*(@TOTSUCC/@TOTATT) END,8,2)
END
Set @vqry='update #finalOutput Set [' + @temptimechar + ']='''+ @kpival+ ''' where NODE =''OVERALL'''
exec(@vqry)
Set @inode =0
END

if @level ='NODE'
BEGIN
ALTER TABLE #FINALOUTPUT
DROP COLUMN ITEM
END
--NO OVERALL VALUE FOR PROCLOAD
if @KPI ='PROCLOAD'
BEGIN
DELETE  FROM #FINALOUTPUT WHERE NODE ='OVERALL'
END
--conditional formatting BEGINS
if @level = 'NODE'
begin
 SET @EXTRA =''
 end
IF @level ='ITEM' 
begin
set @extra=',  [^ITEM^]=[ITEM],  [^ITEM^_BGCOLOR]=''#ffffff'''
end

set @vqry ='begin select [^NODE^]=[NODE],   [^NODE^_BGCOLOR]=''#ffffff'''+@extra+''
set @IDAYS =0
WHILE @iDAYS <@nDAYS 
begin
Set @IDAYS +=1
SELECT @TEMPTIME =STIME FROM #TEMPDATE where IDDATE =@idays
Set @tempTimechar=CAST (convert (varchar,(@tempTime),120) as varchar(32))
 IF @KPI ='PSR' OR @KPI ='3GPSR'
BEGIN
set @vqry =@vqry+',[^'+@temptimechar+'^]=['+@temptimechar+'],
      [^'+@temptimechar+'^_BGCOLOR]= CASE WHEN Convert(real,['+@temptimechar+']) >=99 THEN ''#006400'' WHEN (Convert(real,['+@temptimechar+'])>=98 AND Convert(real,['+@temptimechar+']) <99) THEN ''#7CFC00'' WHEN (Convert(real,['+@temptimechar+'])>=95 AND Convert(real,['+@temptimechar+'])<98) THEN ''#FFFF00''WHEN (Convert(real,['+@temptimechar+'])>=94 AND Convert(real,['+@temptimechar+'])<95) THEN ''#FFA500''WHEN (Convert(real,['+@temptimechar+'])>=92 AND Convert(real,['+@temptimechar+']) <94 ) THEN ''#FF0000''WHEN Convert(real,['+@temptimechar+']) <92 THEN  ''#B22222''end '


END
 
  IF @KPI ='ICHOSR' OR @KPI ='OGHOSR' OR @KPI ='3GICHOSR' OR @KPI='3GOGHOSR' OR @KPI ='3GINTRAHOSR' OR @KPI ='CHANNELALLOC'
BEGIN
set @vqry =@vqry+',[^'+@temptimechar+'^]=['+@temptimechar+'],
      [^'+@temptimechar+'^_BGCOLOR]= CASE WHEN Convert(real,['+@temptimechar+']) >=95 THEN ''#7CFC00'' WHEN (Convert(real,['+@temptimechar+'])>=90 AND Convert(real,['+@temptimechar+']) <95) THEN ''#FFFF00'' WHEN Convert(real,['+@temptimechar+']) <90 THEN  ''#FF0000''end '


END
  IF @KPI ='AUTH' 
BEGIN
set @vqry =@vqry+',[^'+@temptimechar+'^]=['+@temptimechar+'],
      [^'+@temptimechar+'^_BGCOLOR]= CASE WHEN Convert(real,['+@temptimechar+']) >=98 THEN ''#7CFC00'' WHEN (Convert(real,['+@temptimechar+'])>=95 AND Convert(real,['+@temptimechar+']) <98) THEN ''#FFFF00'' WHEN Convert(real,['+@temptimechar+']) <95 THEN  ''#FF0000''end '


END
  IF @KPI ='PROCLOAD' 
BEGIN
set @vqry =@vqry+',[^'+@temptimechar+'^]=['+@temptimechar+'],
      [^'+@temptimechar+'^_BGCOLOR]= CASE  WHEN (Convert(real,['+@temptimechar+'])<70) THEN ''#7CFC00'' WHEN (Convert(real,['+@temptimechar+'])>=70 AND Convert(real,['+@temptimechar+'])<75) THEN ''#FFFF00''WHEN (Convert(real,['+@temptimechar+'])>=75 AND Convert(real,['+@temptimechar+'])<80) THEN ''#FFA500''WHEN (Convert(real,['+@temptimechar+'])>=80 ) THEN ''#FF0000''end '



END
 IF @KPI ='CIPH' OR @KPI ='3GCIPH'OR @KPI ='3GRAB'
BEGIN
set @vqry =@vqry+',[^'+@temptimechar+'^]=['+@temptimechar+'],
      [^'+@temptimechar+'^_BGCOLOR]= CASE WHEN Convert(real,['+@temptimechar+']) >=99 THEN ''#7CFC00'' WHEN Convert(real,['+@temptimechar+'])<99 THEN ''#FF0000'' end '
END
   
    IF @KPI ='EOS' OR @KPI ='VLRA'OR @KPI ='VLRR'
BEGIN
set @vqry =@vqry+',[^'+@temptimechar+'^]=['+@temptimechar+'],
      [^'+@temptimechar+'^_BGCOLOR]=  ''#ffffff'' '
END
   
 IF @KPI ='LUSR' OR @KPI ='3GLUSR'
BEGIN
set @vqry =@vqry+',[^'+@temptimechar+'^]=['+@temptimechar+'],
      [^'+@temptimechar+'^_BGCOLOR]= CASE WHEN Convert(real,['+@temptimechar+']) >=99 THEN ''#006400'' WHEN (Convert(real,['+@temptimechar+'])>=98 AND Convert(real,['+@temptimechar+']) <99) THEN ''#7CFC00'' WHEN (Convert(real,['+@temptimechar+'])>=97 AND Convert(real,['+@temptimechar+'])<98) THEN ''#FFFF00''WHEN (Convert(real,['+@temptimechar+'])>=96 AND Convert(real,['+@temptimechar+'])<97) THEN ''#FFA500''WHEN (Convert(real,['+@temptimechar+'])<96) THEN ''#FF0000''end '

END
 end
set @vqry =@vqry +'from #finaloutput end'

exec(@vqry)

--CONDITIONAL FORMATTING ENDS
select * from #FINALOUTput
drop table #finalOutput
drop table #tempdate
drop table #tempnode
drop table #temp12
END
