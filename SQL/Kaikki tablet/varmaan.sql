SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[profile](
	[ProfileID] [int] IDENTITY(1,1) NOT NULL,
	[etunimi] [varchar](20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[sukunimi] [varchar](20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[nickname] [varchar](20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[password] [varbinary](128) NOT NULL,
	[bio] [varchar](255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[location] [varchar](40) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[username] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[email] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ProfilePhoto varbinary(max),
	[ika] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ProfileID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[kaverit](
	[ProfileID] [int] IDENTITY(1,1) NOT NULL,
	[Kaveri] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)
GO
ALTER TABLE [dbo].[kaverit]  WITH NOCHECK ADD FOREIGN KEY([ProfileID])
REFERENCES [dbo].[profile] ([ProfileID])
GO
ALTER TABLE [dbo].[kaverit]  WITH NOCHECK ADD FOREIGN KEY([ProfileID])
REFERENCES [dbo].[profile] ([ProfileID])
GO
-- BCPArgs:7:[dbo].[profile] in "c:\SQLAzureMW\BCPData\dbo.profile.dat" -E -n -C RAW -b 1000 -a 4096
GO
-- BCPArgs:12:[dbo].[kaverit] in "c:\SQLAzureMW\BCPData\dbo.kaverit.dat" -E -n -C RAW -b 1000 -a 4096
GO

