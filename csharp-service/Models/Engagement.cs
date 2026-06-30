using System.Text.Json.Serialization;

namespace csharp_service.Models;

public class Engagement
{
    [JsonPropertyName("id")]
    public int Id { get; set; }

    [JsonPropertyName("title")]
    public string Title { get; set; } = string.Empty;

    [JsonPropertyName("description")]
    public string? Description { get; set; }

    [JsonPropertyName("client_id")]
    public int ClientId { get; set; }

    [JsonPropertyName("status")]
    public string Status { get; set; } = "ACTIVE";

    [JsonPropertyName("budget")]
    public decimal? Budget { get; set; }

    [JsonPropertyName("deadline")]
    public DateTime? Deadline { get; set; }

    [JsonPropertyName("created_by")]
    public int CreatedBy { get; set; }

    [JsonPropertyName("created_at")]
    public DateTime CreatedAt { get; set; }
}