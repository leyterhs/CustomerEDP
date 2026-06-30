using Microsoft.AspNetCore.Mvc;
using csharp_service.Services;

namespace csharp_service.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ReportController : ControllerBase
{
    private readonly ReportService _reportService;

    public ReportController(ReportService reportService)
    {
        _reportService = reportService;
    }

    [HttpGet("engagement/{id}")]
    public IActionResult GenerateEngagementReport(int id)
    {
        try
        {
            var pdfBytes = _reportService.GenerateEngagementReport(id);
            return File(pdfBytes, "application/pdf", $"Engagement_{id}_Report.pdf");
        }
        catch (Exception ex)
        {
            return BadRequest(new { error = ex.Message });
        }
    }
}