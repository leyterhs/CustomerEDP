using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;
using csharp_service.Data;
using csharp_service.Models;

namespace csharp_service.Services;

public class ReportService
{
    private readonly ApplicationDbContext _context;

    public ReportService(ApplicationDbContext context)
    {
        _context = context;
        QuestPDF.Settings.License = LicenseType.Community;
    }

    public byte[] GenerateEngagementReport(int engagementId)
    {
        var engagement = _context.Engagements.Find(engagementId);
        if (engagement == null)
            throw new Exception("Engagement not found");

        var deliveries = _context.Deliveries
            .Where(d => d.EngagementId == engagementId)
            .ToList();

        var culture = new System.Globalization.CultureInfo("el-GR");

        var document = Document.Create(container =>
        {
            container.Page(page =>
            {
                page.Size(PageSizes.A4);
                page.Margin(2, Unit.Centimetre);
                page.DefaultTextStyle(x => x.FontSize(12));

                page.Header()
                    .Text($"Report: {engagement.Title}")
                    .SemiBold()
                    .FontSize(20)
                    .FontColor(Colors.Blue.Darken2);

                page.Content()
                    .Column(col =>
                    {
                        col.Item().Text($"Status: {engagement.Status}");
                        col.Item().Text($"Budget: {engagement.Budget?.ToString("C", culture) ?? "N/A"}");
                        col.Item().Text($"Deadline: {engagement.Deadline?.ToShortDateString() ?? "N/A"}");
                        col.Item().Text($"Created: {engagement.CreatedAt.ToShortDateString()}");

                        col.Item().PaddingTop(20).Text("Deliveries:").SemiBold();

                        col.Item().Table(table =>
                        {
                            table.ColumnsDefinition(columns =>
                            {
                                columns.RelativeColumn(3);
                                columns.RelativeColumn(2);
                                columns.RelativeColumn(2);
                                columns.RelativeColumn(2);
                                columns.RelativeColumn(2);
                            });

                            table.Header(header =>
                            {
                                header.Cell().Text("Title").SemiBold();
                                header.Cell().Text("Priority").SemiBold();
                                header.Cell().Text("Status").SemiBold();
                                header.Cell().Text("Due Date").SemiBold();
                                header.Cell().Text("Created").SemiBold();
                            });

                            foreach (var d in deliveries)
                            {
                                table.Cell().Text(d.Title);
                                table.Cell().Text(d.Priority);
                                table.Cell().Text(d.Status);
                                table.Cell().Text(d.DueDate?.ToShortDateString() ?? "N/A");
                                table.Cell().Text(d.CreatedAt.ToShortDateString());
                            }
                        });
                    });

                page.Footer()
                    .Text($"Generated on {DateTime.Now}")
                    .FontSize(10)
                    .AlignRight();
            });
        });

        return document.GeneratePdf();
    }
}